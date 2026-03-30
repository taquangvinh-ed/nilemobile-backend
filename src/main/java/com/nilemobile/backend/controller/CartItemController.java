package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.dto.request.AddCartItemRequest;
import com.nilemobile.backend.dto.CartItemDTO;
import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.dto.request.BuyNowRequest;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart/items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddCartItemRequest req) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        if (req.getVariation() == null || req.getVariation().getId() == null) {
            throw new CartItemException("Variation or Variation ID cannot be null");
        }

        Variation variation = variationRepository.findById(req.getVariation().getId())
                .orElseThrow(() -> new CartItemException("Variation not found with ID: " + req.getVariation().getId()));

        CartItem cartItem = new CartItem();
        cartItem.setVariation(req.getVariation());
        cartItem.setCart(cart);
        CartItem createdCartItem = cartItemService.createCartItem(cartItem, userId);

        CartItemDTO cartItemDTO = convertToDTO(createdCartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        CartItem updatedCartItem = cartItemService.updateCartItem(userId, cartItemId, quantity);

        CartItemDTO cartItemDTO = convertToDTO(updatedCartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        cartItemService.removeCartItem(userId, cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/buy-now")
    public ResponseEntity<CartItemDTO> buyNow(
            @RequestHeader("Authorization") String jwt,
            @RequestBody BuyNowRequest request) throws CartItemException {
        // Lấy user từ JWT
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        // Tìm hoặc tạo giỏ hàng cho user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Kiểm tra variationId
        if (request.getVariationId() == null) {
            throw new CartItemException("Variation ID cannot be null");
        }

        // Tìm variation
        Variation variation = variationRepository.findById(request.getVariationId())
                .orElseThrow(() -> new CartItemException("Variation not found with ID: " + request.getVariationId()));

        // Kiểm tra xem variation đã có trong giỏ hàng chưa
        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getVariation().getId().equals(request.getVariationId()))
                .findFirst()
                .orElse(null);

        CartItem cartItem;
        if (existingCartItem != null) {
            // Nếu đã có, cập nhật isSelected
            existingCartItem.setSelected(true);
            cartItem = cartService.updateCartItem(existingCartItem);
        } else {
            // Nếu chưa có, tạo mới CartItem với isSelected: true
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVariation(variation);
            cartItem.setQuantity(1);
            cartItem.setSubtotal(variation.getPrice() * cartItem.getQuantity());
            cartItem.setDiscountPrice(variation.getDiscountPrice() * cartItem.getQuantity());
            cartItem.setSelected(true); // Đặt isSelected: true
            cartItem = cartItemService.createCartItem(cartItem, userId);
        }

        CartItemDTO cartItemDTO = convertToDTO(cartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}/select")
    public ResponseEntity<CartItemDTO> updateCartItemSelection(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestParam Boolean selected) throws CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        CartItem updatedCartItem = cartItemService.updateCartItemSelection(userId, cartItemId, selected);

        CartItemDTO cartItemDTO = convertToDTO(updatedCartItem);
        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }


    private CartItemDTO convertToDTO(CartItem cartItem) {
        VariationDTO variationDTO = new VariationDTO(cartItem.getVariation());
        long subtotal = cartItem.getVariation().getPrice() * cartItem.getQuantity();
        long discountPrice = (long) (cartItem.getVariation().getDiscountPrice() * cartItem.getQuantity());

        CartItemDTO cartItemDTO = new CartItemDTO(
                variationDTO.getName(),
                cartItem.getId(),
                variationDTO,
                cartItem.getQuantity(),
                subtotal,
                discountPrice,
                cartItem.getSelected()
        );
        return cartItemDTO;
    }
}
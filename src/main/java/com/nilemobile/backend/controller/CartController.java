package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.CartDTO;
import com.nilemobile.backend.reponse.CartItemDTO;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getUserId());

        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(cartItem -> {
            VariationDTO variationDTO = new VariationDTO(cartItem.getVariation());
            return new CartItemDTO(variationDTO.getName(), cartItem.getId(), variationDTO, cartItem.getQuantity(), cartItem.getSubtotal(), cartItem.getDiscountPrice(), cartItem.getSelected());
        }).collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO(cart.getSubtotal(),
                cart.getTotalDiscountPrice(),
                cart.getTotalDiscountPercent(),
                cart.getTotalItems(),
                cartItemDTOs);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

   
}

package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.CartItemDTO;
import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.dto.request.CreateCartItemRequest;
import com.nilemobile.backend.mapper.CartItemMapper;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.CartItemRepository;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImp implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final CartRepository cartRepository;

    private final VariationRepository variationRepository;


    @Override
    @Transactional
    public CartItemDTO createCartItem(CreateCartItemRequest request) {
        if (request == null || request.getVariationId() == null) {
            throw new CartItemException("Variation ID cannot be null");
        }
        if (request.getCartId() == null) {
            throw new CartItemException("Cart ID cannot be null");
        }

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new CartItemException("Cart not found with ID: " + request.getCartId()));

        Variation variation = variationRepository.findById(request.getVariationId())
                .orElseThrow(() -> new CartItemException("Variation not found with ID: " + request.getVariationId()));

        if (variation.isDeleted()) {
            throw new CartItemException("Variation with ID " + variation.getVariationId() + " has been deleted");
        }

        int quantity = request.getQuantity() != null && request.getQuantity() > 0
                ? request.getQuantity() : 1;
        validateStock(variation, quantity);

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getVariation() != null
                        && item.getVariation().getVariationId().equals(variation.getVariationId()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + quantity;
            validateStock(variation, newQuantity);
            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setSubtotal(calculateSubtotal(newQuantity, variation.getPrice() != null ? variation.getPrice() : 0L));
            CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
            return cartItemMapper.toDto(updatedCartItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setVariation(variation);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(calculateSubtotal(quantity, variation.getPrice() != null ? variation.getPrice() : 0L));
        cart.getCartItems().add(cartItem);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(savedCartItem);
    }

    @Override
    @Transactional
    public CartItemDTO updateCartItem(Long userId, Long cartItemId, int quantity) throws CartItemException {
        if (quantity <= 0) {
            throw new CartItemException("Quantity must be greater than zero");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));
        validateCartItemOwnership(cartItem, userId);

        Variation variation = cartItem.getVariation();
        if (variation == null) {
            throw new CartItemException("CartItem with id " + cartItemId + " has no variation");
        }
        if (variation.isDeleted()) {
            throw new CartItemException("Variation with ID " + variation.getVariationId() + " has been deleted");
        }
        validateStock(variation, quantity);

        cartItem.setQuantity(quantity);
        cartItem.setSubtotal(calculateSubtotal(quantity, variation.getPrice() != null ? variation.getPrice() : 0L));
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(updatedCartItem);
    }

    @Override
    @Transactional
    public void removeCartItemFromCart(Long userId, Long cartItemId) throws CartItemException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));
        validateCartItemOwnership(cartItem, userId);

        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void updateCartItemSelection(Long userId, Long cartItemId, Boolean selected) throws CartItemException {
        if (selected == null) {
            throw new CartItemException("Selection status cannot be null");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));
        validateCartItemOwnership(cartItem, userId);
        cartItem.setSelected(selected);
        cartItemRepository.save(cartItem);
    }

    private void validateCartItemOwnership(CartItem cartItem, Long userId) {
        User owner = getCartOwner(cartItem.getCart());
        if (owner == null || !owner.getUserId().equals(userId)) {
            throw new CartItemException("CartItem does not belong to user with id: " + userId);
        }
    }


    private User getCartOwner(Cart cart) {
        Customer customer = cart.getCustomer();
        return customer != null ? customer.getUser() : null;
    }

    private void validateStock(Variation variation, int quantity) {
        if (quantity > variation.getStockQuantity()) {
            throw new CartItemException("Requested quantity " + quantity
                    + " exceeds available stock " + variation.getStockQuantity());
        }
    }

    private long calculateSubtotal(int quantity, long itemPrice) {
        return quantity * itemPrice;
    }
}

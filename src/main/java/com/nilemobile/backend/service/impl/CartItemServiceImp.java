package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.CartItemRepository;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImp implements CartItemService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final CartRepository cartRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem, Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new CartItemException("User not found for ID: " + userId);
        }

        if (cartItem == null || cartItem.getCart() == null) {
            throw new CartItemException("CartItem or Cart cannot be null");
        }

        Cart cart = cartRepository.findByCartIdWithItems(cartItem.getCart().getCartId())
                .orElseThrow(() -> new CartItemException("Cart not found with ID: " + cartItem.getCart().getCartId()));

        if (cart.getUser() != null && !cart.getUser().getUserId().equals(userId)) {
            throw new CartItemException("Cart with ID " + cart.getCartId() + " belongs to another user.");
        }

        if (cart.getUser() == null) {
            cart.setUser(user);
        }

        boolean exists = isCartItemExist(cart, cartItem.getVariation(), userId);
        if (exists) {
            CartItem existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getVariation().getId().equals(cartItem.getVariation().getId()))
                    .findFirst()
                    .orElse(null);
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                long price = existingCartItem.getVariation().getPrice();
                existingCartItem.setSubtotal(existingCartItem.getQuantity() * price);
                CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
                cart = updatedCartItem.getCart();
                cart.calculateSubtotal();
                cartRepository.save(cart);
                return updatedCartItem;
            }
        }

        cartItem.setQuantity(1);
        cartItem.setDiscountPrice(cartItem.getQuantity() * cartItem.getVariation().getDiscountPrice());
        if (cartItem.getVariation() != null) {
            long price = cartItem.getVariation().getPrice();
            cartItem.setSubtotal(cartItem.getQuantity() * price);
        } else {
            cartItem.setSubtotal(0L);
        }
        cartItem.setCart(cart);
        CartItem saveCartItem = cartItemRepository.save(cartItem);
        cart.calculateSubtotal();
        cartRepository.save(cart);
        return saveCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, int quantity) throws CartItemException {
        CartItem cartItem = cartItemRepository.findById(cartItemId).
                orElseThrow(() -> new CartItemException("CartItem not found with id:" + cartItemId));

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new CartItemException("CartItem does not belong to user with id: " + userId);
        }

        cartItem.setQuantity(quantity);

        if (cartItem.getVariation() != null) {
            long price = cartItem.getVariation().getPrice();
            cartItem.setSubtotal(price * cartItem.getQuantity());
        }

        CartItem updateCartItem = cartItemRepository.save(cartItem);
        Cart cart = updateCartItem.getCart();
        cart.calculateSubtotal();
        cartRepository.save(cart);
        return updateCartItem;
    }

    @Override
    public Boolean isCartItemExist(Cart cart, Variation variation, Long userId) {
        if (cart == null || variation == null || variation.getId() == null) {
            return false;
        }
        return cart.getCartItems().stream()
                .anyMatch(item -> item.getVariation() != null && item.getVariation().
                        getId().equals(variation.getId()));
    }

    @Override
    public CartItem removeCartItem(Long userId, Long cartItemId) throws CartItemException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));
        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new CartItemException("CartItem does not belong to user with id: " + userId);
        }
        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.calculateSubtotal();
        cartRepository.save(cart);
        return cartItem;
    }

    @Override
    public CartItem updateCartItemSelection(Long userId, Long cartItemId, Boolean selected) throws CartItemException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("CartItem not found with id: " + cartItemId));

        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            throw new CartItemException("CartItem does not belong to user with id: " + userId);
        }

        cartItem.setSelected(selected);
        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        Cart cart = updatedCartItem.getCart();
        cart.calculateSubtotal();
        cartRepository.save(cart);

        return updatedCartItem;
    }
}

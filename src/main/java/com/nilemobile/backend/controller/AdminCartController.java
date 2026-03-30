package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.dto.AdminCartDTO;
import com.nilemobile.backend.dto.AdminCartItemDTO;
import com.nilemobile.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/admin/carts")
public class AdminCartController {

    @Autowired
    private CartRepository cartRepository;
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<AdminCartDTO>> getCartByUserId(@PathVariable Long userId) {
        List<Cart> cartItems = cartRepository.findByUserUserId(userId);
        List<AdminCartDTO> cartDTOS = cartItems.stream().map(cart -> new AdminCartDTO(
                cart.getCartId(),
                cart.getSubtotal(),
                cart.getTotalDiscountPrice(),
                cart.getTotalDiscountPercent(),
                cart.getTotalItems(),
                cart.getCartItems().stream().map(cartItem -> new AdminCartItemDTO(
                        cartItem.getId(),
                        cartItem.getVariation().getId(),
                        cartItem.getVariation().getVariationName(),
                        cartItem.getVariation().getImageURL(),
                        cartItem.getQuantity(),
                        cartItem.getSubtotal(),
                        cartItem.getDiscountPrice()
                )).collect(toList())
        )).toList();
        return ResponseEntity.ok(cartDTOS);
    }
}

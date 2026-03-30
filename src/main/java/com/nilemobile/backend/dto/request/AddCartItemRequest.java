package com.nilemobile.backend.dto.request;

import com.nilemobile.backend.model.Cart;
import com.nilemobile.backend.model.Variation;

public class AddCartItemRequest {
    private Variation variation;
    private Cart cart;


    public AddCartItemRequest(Variation variation, Cart cart) {
        this.variation = variation;
        this.cart = cart;

    }

    public Variation getVariation() {
        return variation;
    }

    public void setVariation(Variation variation) {
        this.variation = variation;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

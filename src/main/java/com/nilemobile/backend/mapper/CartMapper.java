package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.CartDTO;
import com.nilemobile.backend.model.Cart;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    Cart toEntity(com.nilemobile.backend.dto.CartDTO cartDTO);

    @AfterMapping
    default void linkCartItems(@MappingTarget Cart cart) {
        cart.getCartItems().forEach(cartItem -> cartItem.setCart(cart));
    }


    CartDTO toDto(Cart cart);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cart partialUpdate(com.nilemobile.backend.dto.CartDTO cartDTO, @MappingTarget Cart cart);

    @AfterMapping
    default void setTotalItem(Cart cart, @MappingTarget CartDTO cartDTO) {
        int totalItems = cart.getCartItems().stream()
                .mapToInt(com.nilemobile.backend.model.CartItem::getQuantity)
                .sum();
        cartDTO.setTotalItems(totalItems);
    }
}
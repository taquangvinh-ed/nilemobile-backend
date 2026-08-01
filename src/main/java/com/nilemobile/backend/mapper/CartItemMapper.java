package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.CartItemDTO;
import com.nilemobile.backend.model.CartItem;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItem toEntity(CartItemDTO cartItemDTO);

    CartItemDTO toDto(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(CartItemDTO cartItemDTO, @MappingTarget CartItem cartItem);
}
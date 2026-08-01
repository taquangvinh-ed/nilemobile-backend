package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.OrderDetailDTO;
import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.model.OrderDetail;
import org.mapstruct.*;

import java.util.HashMap;
import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailMapper {

    @Mapping(target = "productId", source = "variation.product.productId")
    @Mapping(target = "productName", source = "variation.product.productName")
    @Mapping(target = "variationId", source = "variation.variationId")
    @Mapping(target = "variationName", source = "variation.variationName")
    @Mapping(target = "categoryInfo", source = "variation.product.category")
    @Mapping(target = "subtotal", expression = "java(orderDetail.getPrice() * orderDetail.getQuantity())")
    OrderDetailDTO toDto(OrderDetail orderDetail);

    default Map<String, String> mapCategoryInfo(Category category) {
        if (category == null) {
            return null;
        }
        Map<String, String> info = new HashMap<>();
        for (Category current = category; current != null; current = current.getParentCategory()) {
            info.put("categoryLevel" + current.getLevel(), current.getName());
        }
        return info;
    }
}
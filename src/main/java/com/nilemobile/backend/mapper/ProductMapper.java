package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.request.UpdateProductRequest;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.dto.ProductDTO;
import com.nilemobile.backend.dto.request.CreateProductRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "variations", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(CreateProductRequest request);

    @AfterMapping
    default void linkVariations(@MappingTarget Product product) {
        product.getVariations().forEach(variation -> variation.setProduct(product));
    }

    ProductDTO toDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product partialUpdate(UpdateProductRequest request, @MappingTarget Product product);
}
package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.ReviewDTO;
import com.nilemobile.backend.model.Review;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    Review toEntity(ReviewDTO reviewDTO);

    @Mapping(target = "customerName", expression = "java(review.getCustomer() != null ? review.getCustomer().getFirstName() + \" \" + review.getCustomer().getLastName() : null)")
    @Mapping(target = "username", source = "customer.user.username")
    @Mapping(target = "productName", source = "variation.product.productName")
    @Mapping(target = "variationName", source = "variation.variationName")
    ReviewDTO toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Review partialUpdate(ReviewDTO reviewDTO, @MappingTarget Review review);

    List<ReviewDTO> toDtoList(List<Review> reviews);
}
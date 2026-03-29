package com.nilemobile.backend.mapper;

import com.nilemobile.backend.model.Category;
import com.nilemobile.backend.reponse.CategoryDTO;
import com.nilemobile.backend.request.CreateCategoryRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriesMapper {
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "categoryId", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    @Mapping(source = "parentCategory.categoryId", target = "categoryParentId")
    CategoryDTO toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDTO categoryDTO, @MappingTarget Category category);
}
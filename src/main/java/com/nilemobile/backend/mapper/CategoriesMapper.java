package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.CategoryDTO;
import com.nilemobile.backend.dto.request.CreateCategoryRequest;
import com.nilemobile.backend.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriesMapper {

    @Mapping(target = "parentCategory", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryLevel", target = "level")
    @Mapping(target = "parentCategory", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    @Mapping(source = "parentCategory.categoryId", target = "categoryParentId")
    CategoryDTO toDto(Category category);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    Category partialUpdate(CategoryDTO categoryDTO, @MappingTarget Category category);

    List<CategoryDTO> toDtoList(List<Category> categories);

}
package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.CategoryDTO;
import com.nilemobile.backend.dto.request.CreateCategoryRequest;
import com.nilemobile.backend.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriesMapper {

    Category toEntity(CategoryDTO categoryDTO);

    Category toEntity(CreateCategoryRequest request);

    CategoryDTO toDto(Category category);

    Category partialUpdate(CategoryDTO categoryDTO, Category category);

    List<CategoryDTO> toDtoList(List<Category> categories);
}
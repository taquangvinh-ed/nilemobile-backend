package com.nilemobile.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryRequest {
    private String categoryName;
    private Long categoryParentId;
    private int categoryLevel;
}

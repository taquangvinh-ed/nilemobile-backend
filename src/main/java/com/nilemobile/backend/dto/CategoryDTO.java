package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private Long categoryParentId;
    private int level;
}

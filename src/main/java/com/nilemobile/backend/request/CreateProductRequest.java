package com.nilemobile.backend.request;

import com.nilemobile.backend.model.Variation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequest {
    private String productName;
    private String description;
    private Map<String, Object> attributes;
    private String firstLevel;
    private String secondLevel;
    private String thirdLevel;
}

package com.nilemobile.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String customerName;
    private String username;
    private String productName;
    private String variationName;
    private String content;
    private Float rating;
    private LocalDateTime createdAt;
}

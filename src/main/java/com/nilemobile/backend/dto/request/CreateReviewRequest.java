package com.nilemobile.backend.dto.request;

public class CreateReviewRequest {
    private Long userId;
    private Long variationId;
    private String content;
    private Float rating;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}

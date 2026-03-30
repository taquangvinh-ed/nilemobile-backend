package com.nilemobile.backend.dto;

import com.nilemobile.backend.model.Review;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long variationId;
    private String content;
    private Float rating;
    private LocalDateTime createdAt;

    public ReviewDTO(Long id, Long userId, String username, Long variationId, String content, Float rating, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.variationId = variationId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getUserId();
        String firstName = review.getUser() != null ? review.getUser().getFirstName() : "";
        String lastName = review.getUser() != null ? review.getUser().getLastName() : "";
        this.username = (lastName != null ? lastName : "") + " " + (firstName != null ? firstName : "");
        if (this.username.trim().isEmpty()) {
            this.username = "Ẩn danh";
        }
        this.variationId = review.getVariation().getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

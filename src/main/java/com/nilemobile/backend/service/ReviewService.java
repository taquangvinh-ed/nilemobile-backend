package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.ReviewDTO;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.dto.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(CreateReviewRequest request);

    void deleteReview(Long userId, Long reviewId);

    List<ReviewDTO> getAllReview(Long productId) throws ProductException;
}

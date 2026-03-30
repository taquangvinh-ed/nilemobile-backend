package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.dto.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(CreateReviewRequest request);

    public void deleteReview(Long userId, Long reviewId);

    List<Review> getAllReview(Variation variation) throws ProductException;
}

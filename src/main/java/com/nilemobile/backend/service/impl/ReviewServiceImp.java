package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.ReviewRepository;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.request.CreateReviewRequest;
import com.nilemobile.backend.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private VariationRepository variationRepository;

    public ReviewServiceImp(ReviewRepository reviewRepository, UserRepository userRepository, VariationRepository variationRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.variationRepository = variationRepository;
    }

    @Override
    public Review createReview(CreateReviewRequest request) {
        if (request == null || request.getVariationId() == null || request.getUserId() == null) {
            throw new ProductException("Invalid request data");
        }

        Variation variation = variationRepository.findById(request.getVariationId())
                .orElseThrow(() -> new ProductException("Variation not found with id: " + request.getVariationId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ProductException("User not found with id: " + request.getUserId()));

        Review review = new Review();
        review.setVariation(variation);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setContent(request.getContent());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ProductException("Review not found with id: " + reviewId));

        if (!review.getUser().getUserId().equals(userId)) {
            throw new ProductException("Unauthorized to delete this review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public List<Review> getAllReview(Variation variation) throws ProductException {
        if (variation == null || variation.getId() == null) {
            throw new ProductException("Invalid variation");
        }
        return reviewRepository.findByVariation(variation);
    }
}

package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.dto.ReviewDTO;
import com.nilemobile.backend.dto.request.CreateReviewRequest;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.mapper.ReviewMapper;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.OrderRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.repository.ReviewRepository;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final VariationRepository variationRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ReviewDTO createReview(CreateReviewRequest request) {
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new ProductException("Rating must be between 1 and 5");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ProductException("User not found with id: " + request.getUserId()));
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new ProductException("Customer not found for user with id: " + request.getUserId());
        }

        Variation variation = variationRepository.findById(request.getVariationId())
                .orElseThrow(() -> new ProductException("Variation not found with id: " + request.getVariationId()));

        boolean purchased = orderRepository.existsPurchasedVariation(
                request.getUserId(),
                request.getVariationId(),
                List.of(OrderStatus.DELIVERED, OrderStatus.COMPLETED));
        if (!purchased) {
            throw new ProductException("You can only review a product you have purchased");
        }

        Review review = new Review();
        review.setCustomer(customer);
        review.setVariation(variation);
        review.setContent(request.getContent());
        review.setRating(request.getRating());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ProductException("Review not found with id: " + reviewId));

        Customer customer = review.getCustomer();
        if (customer == null || customer.getUser() == null || !customer.getUser().getUserId().equals(userId)) {
            throw new ProductException("Unauthorized to delete this review");
        }

        reviewRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReview(Long productId) throws ProductException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviewMapper.toDtoList(reviews);
    }
}

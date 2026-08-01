package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.ReviewDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.request.CreateReviewRequest;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.service.ReviewService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final UserService userService;

    private final VariationRepository variationRepository;

    @PostMapping
    public ApiResponse<ReviewDTO> createReview(@RequestBody CreateReviewRequest request,
                                               @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        request.setUserId(user.getUserId());

        ReviewDTO reviewDTO = reviewService.createReview(request);

        return ApiResponse.<ReviewDTO>builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(reviewDTO)
                .build();
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId,
                                          @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);

        reviewService.deleteReview(user.getUserId(), reviewId);

        return ApiResponse.<Void>builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    @GetMapping("/variation/{variationId}")
    public ApiResponse<List<ReviewDTO>> getReviewsByVariation(@PathVariable Long variationId) {
        Variation variation = variationRepository.findById(variationId)
                .orElseThrow(() -> new ProductException("Variation not found with id: " + variationId));

        List<ReviewDTO> reviewDTOs = reviewService.getAllReview(variation.getProduct().getProductId());

        return ApiResponse.<List<ReviewDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(reviewDTOs)
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewDTO>> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewDTO> reviewDTOs = reviewService.getAllReview(productId);

        return ApiResponse.<List<ReviewDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(reviewDTOs)
                .build();
    }
}

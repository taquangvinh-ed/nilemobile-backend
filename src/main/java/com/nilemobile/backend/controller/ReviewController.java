package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Review;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.dto.ReviewDTO;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.dto.request.CreateReviewRequest;
import com.nilemobile.backend.service.ReviewService;
import com.nilemobile.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private UserService userService;
    private VariationRepository variationRepository;

    public ReviewController(ReviewService reviewService,
                            UserService userService,
                            VariationRepository variationRepository) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.variationRepository = variationRepository;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody CreateReviewRequest req,
                                                  @RequestHeader("Authorization") String jwt) {

        System.out.println("Received request: " + req + ", JWT: " + jwt);
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();
        req.setUserId(userId);
        try {
            Review createdReview = reviewService.createReview(req);
            ReviewDTO reviewDTO = new ReviewDTO(createdReview);
            return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
        } catch (ProductException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteeReview(@PathVariable Long reviewId,
                                              @RequestHeader("Authorization") String jwt) {

        User user = userService.findUserProfileByJwt(jwt);

        Long userId = user.getUserId();
        ;

        try {
            reviewService.deleteReview(userId, reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/variation/{variationId}")
    public ResponseEntity<?> getReviewsByVariation(@PathVariable Long variationId) {
        try {
            Optional<Variation> variation = variationRepository.findById(variationId);
            List<Review> reviews = reviewService.getAllReview(variation.get());
            List<ReviewDTO> reviewDTOs = reviews.stream()
                    .map(ReviewDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


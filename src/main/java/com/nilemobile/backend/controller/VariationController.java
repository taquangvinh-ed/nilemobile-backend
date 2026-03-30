package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.service.VariationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/variations")
@RequiredArgsConstructor
public class VariationController {

    private final VariationService variationService;

    @PostMapping
    public ApiResponse<?> createVariation(@RequestParam Long productId, @Valid @RequestBody VariationDTO request){
        var createdVariation = variationService.createVariation(productId, request);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .body(createdVariation)
                .build();
    }

    @PutMapping
    public ApiResponse<?> updateVariation(@RequestParam Long variationId, @Valid @RequestBody VariationDTO request){
        var updatedVariation = variationService.updateVariation(variationId, request);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .body(updatedVariation)
                .build();
    }

    @DeleteMapping("/{variationId}")
    public ApiResponse<?> deleteVariation(@PathVariable Long variationId) {
        variationService.deleteVariation(variationId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
    }

    @DeleteMapping("/soft/{variationId}")
    public ApiResponse<?> deleteVariationSoft(@PathVariable Long variationId) {
        variationService.deleteVariationSoft(variationId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<?> getVariationsByProductId(@PathVariable Long productId) {
        var variations = variationService.getAllVariationsByProductId(productId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .body(variations)
                .build();

    }
}
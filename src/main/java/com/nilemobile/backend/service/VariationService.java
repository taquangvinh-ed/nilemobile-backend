package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.request.CreateVariationRequest;

import java.util.List;

public interface VariationService {
    VariationDTO createVariation(Long productId, CreateVariationRequest request);

    VariationDTO updateVariation(Long variationId, VariationDTO);

    void deleteVariation(Long variationId);

    List<VariationDTO> getAllVariationsByProductId(Long productId);
}

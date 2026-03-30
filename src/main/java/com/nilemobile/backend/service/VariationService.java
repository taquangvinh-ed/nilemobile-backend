package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.VariationDTO;

import java.util.List;

public interface VariationService {
    VariationDTO createVariation(Long productId, VariationDTO request);

    VariationDTO updateVariation(Long variationId, VariationDTO request);

    void deleteVariation(Long variationId);

    void deleteVariationSoft(Long variationId);

    List<VariationDTO> getAllVariationsByProductId(Long productId);
}

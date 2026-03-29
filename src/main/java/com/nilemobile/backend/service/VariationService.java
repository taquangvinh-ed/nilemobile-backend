package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.reponse.VariationDTO2;
import com.nilemobile.backend.request.CreateVariationRequest;

import java.util.List;

public interface VariationService {
    VariationDTO createVariation(Long productId, CreateVariationRequest request);

    VariationDTO updateVariation(Long variationId, VariationDTO);

    void deleteVariation(Long variationId);

    List<VariationDTO> getAllVariationsByProductId(Long productId);
}

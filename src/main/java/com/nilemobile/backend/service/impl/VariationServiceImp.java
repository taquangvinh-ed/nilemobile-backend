package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.reponse.VariationDTO;
import com.nilemobile.backend.reponse.VariationDTO2;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.request.CreateVariationRequest;
import com.nilemobile.backend.service.ProductService;
import com.nilemobile.backend.service.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationServiceImp implements VariationService {

    private final VariationRepository variationRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;


    @Override
    public VariationDTO createVariation(Long productId, CreateVariationRequest request) {
        return null;
    }

    @Override
    public VariationDTO updateVariation(Long variationId) {
        return null;
    }

    @Override
    public void deleteVariation(Long variationId) {

    }

    @Override
    public List<VariationDTO> getAllVariationsByProductId(Long productId) {
        return List.of();
    }
}

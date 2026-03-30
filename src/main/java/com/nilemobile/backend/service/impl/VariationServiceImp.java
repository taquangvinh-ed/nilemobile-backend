package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.mapper.VariationMapper;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
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
    private final VariationMapper variationMapper;
    private final ProductRepository productRepository;


    @Override
    public VariationDTO createVariation(Long productId, VariationDTO request) {
        Product product = productService.findProductById(productId);
        Variation variation = variationMapper.toEntity(request);
        variation.setProduct(product);
        Variation savedVariation = variationRepository.save(variation);
        return variationMapper.toDto(savedVariation);
    }

    @Override
    public VariationDTO updateVariation(Long variationId, VariationDTO request) {
        var existingVariation = findVariationById(variationId);
        var updatedVariation = variationMapper.partialUpdate(request, existingVariation);
        var savedVariation = variationRepository.save(updatedVariation);
        return variationMapper.toDto(savedVariation);
    }


    @Override
    public void deleteVariation(Long variationId) {
        variationRepository.deleteById(variationId);
    }

    @Override
    public void deleteVariationSoft(Long variationId) {
        var existingVariation = findVariationById(variationId);
        existingVariation.setDeleted(true);
        variationRepository.save(existingVariation);
    }

    Variation findVariationById(Long variationId) {
        return variationRepository.findById(variationId)
                .orElseThrow(() -> new VariationException(ErrorCode.VARIATION_NOT_FOUND.getMessage()));
    }


    @Override
    public List<VariationDTO> getAllVariationsByProductId(Long productId) {
        List<Variation> variations = variationRepository.findByProduct_ProductIdAndIsDeletedFalse(productId);
        return variations.stream().map(variationMapper::toDto).toList();
    }
}

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariationServiceImp implements VariationService {

    private VariationRepository variationRepository;
    private ProductService productService;
    private ProductRepository productRepository;


    public VariationServiceImp(VariationRepository variationRepository, ProductService productService, ProductRepository productRepository) {
        this.variationRepository = variationRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Variation findVariationById(Long variationId) throws ProductException {
        if (variationId == null) {
            throw new IllegalArgumentException("Variation ID cannot be null");
        }
        return variationRepository.findById(variationId)
                .orElseThrow(() -> new ProductException("Variation not found with id: " + variationId));
    }

    @Override
    public List<VariationDTO2> getAllVariations(){
        List<Variation> variations = variationRepository.findAll();
        return variations.stream().
                map(variation -> new VariationDTO2(
                        variation.getId(),
                        variation.getProduct().getName(),
                        variation.getProduct().getCategory().getName(),
                        variation.getProduct().getCategory().getParentCategory().getName(),
                        variation.getColor(),
                        variation.getRam(),
                        variation.getRom(),
                        variation.getPrice(),
                        variation.getDiscountPrice(),
                        variation.getDiscountPercent(),
                        variation.getStockQuantity(),
                        variation.getImageURL())).toList();
    }

    @Override
    public List<VariationDTO> getVariationsByProductId(Long productId) throws VariationException {
        return variationRepository.findByProductId(productId);
    }

    @Override
    public void deleteVariationById(Long variationId) {
        if (variationRepository.existsById(variationId)) {
            variationRepository.deleteById(variationId);
        }
    }

    @Override
    public Variation updateVariation(Long variationId, VariationDTO variationDTO) throws VariationException {
        if (variationDTO.getColor() == null || variationDTO.getColor().isEmpty() ||
                variationDTO.getRam() == null || variationDTO.getRam().isEmpty() ||
                variationDTO.getRom() == null || variationDTO.getRom().isEmpty() ||
                variationDTO.getPrice() == null ||
                variationDTO.getDiscountPrice() == null ||
                variationDTO.getStockQuantity() == null) {
            throw new VariationException("Thông tin cập nhật không được để trống (trừ imageURL có thể rỗng).");
        }

        Variation variation = findVariationById(variationId);

        variation.setColor(variationDTO.getColor());
        variation.setRam(variationDTO.getRam());
        variation.setRom(variationDTO.getRom());
        variation.setPrice(variationDTO.getPrice());
        variation.setDiscountPrice(variationDTO.getDiscountPrice());
        variation.setDiscountPercent(variationDTO.getDiscountPercent());
        variation.setStockQuantity(variationDTO.getStockQuantity());
        variation.setImageURL(variationDTO.getImageURL());

        return variationRepository.save(variation);
    }

    @Override
    public boolean isVariationInStock(Long variationId, int quantity) throws ProductException {
        if (variationId == null || quantity < 0) {
            throw new IllegalArgumentException("Variation ID or quantity is invalid");
        }
        Variation variation = findVariationById(variationId);
        return variation.getStockQuantity() != null && variation.getStockQuantity() >= quantity;
    }

    @Override
    public Variation updateVariationStock(Long variationId, int quantity) throws ProductException {
        if (variationId == null || quantity < 0) {
            throw new IllegalArgumentException("Variation ID or quantity is invalid");
        }
        Variation variation = findVariationById(variationId);
        Integer currentStock = variation.getStockQuantity();
        if (currentStock == null || currentStock < quantity) {
            throw new ProductException("Insufficient stock for variation with id: " + variationId);
        }
        variation.setStockQuantity(currentStock - quantity);
        return variationRepository.save(variation);
    }

    @Override
    public List<Variation> findVariationsByProductId(Long productId) throws ProductException {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product product = productService.findProductById(productId);
        return variationRepository.findByProduct(product);
    }

    @Override
    public Variation updateVariationPrice(Long variationId, Long newPrice) throws ProductException {
        if (variationId == null || newPrice == null || newPrice < 0) {
            throw new IllegalArgumentException("Variation ID or new price is invalid");
        }
        Variation variation = findVariationById(variationId);
        variation.setPrice(newPrice);
        return variationRepository.save(variation);
    }

    @Override
    public Variation createVariation(CreateVariationRequest request) throws ProductException {
        if (request == null) {
            throw new IllegalArgumentException("CreateVariationRequest cannot be null");
        }

        Product product = productService.findProductById(request.getProductId());

        Variation variation = new Variation();
        variation.setProduct(product);
        variation.setColor(request.getColor());
        variation.setRam(request.getRam());
        variation.setRom(request.getRom());
        variation.setPrice(request.getPrice());
        variation.setDiscountPrice(request.getDiscountPrice());
        variation.setDiscountPercent(request.getDiscountPercent());
        variation.setStockQuantity(request.getStockQuantity());
        variation.setImageURL(request.getImageURL());

        Variation savedVariation = variationRepository.save(variation);

        product.getVariations().add(savedVariation);
        productRepository.save(product);

        return savedVariation;
    }
}

package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.exception.VariationException;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.request.CreateVariationRequest;
import com.nilemobile.backend.service.VariationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/variation")
public class VariationController {
    private VariationService variationService;

    public VariationController(VariationService variationService) {
        this.variationService = variationService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<VariationDTO2>> getAllVariations(){
        List<VariationDTO2> variations = variationService.getAllVariations();
        return ResponseEntity.ok(variations);
    }

    @GetMapping("/id/{variationId}")
    public ResponseEntity<VariationDTO> getVariationById(@PathVariable Long variationId) throws VariationException{
        Variation variation = variationService.findVariationById(variationId);
        VariationDTO variationDTO = new VariationDTO(variation);
        return ResponseEntity.ok(variationDTO);
    }

    @GetMapping("/productId/{productId}")
    public List<VariationDTO> getVariationsByProductId(@PathVariable Long productId) throws VariationException {
        return variationService.getVariationsByProductId(productId);
    }

    @PostMapping("/create")
    public ResponseEntity<VariationDTO> createVariation(@Valid @RequestBody CreateVariationRequest request) throws ProductException {
        Variation variation = variationService.createVariation(request);
        VariationDTO variationDTO = new VariationDTO(variation);
        return ResponseEntity.status(HttpStatus.CREATED).body(variationDTO);
    }

    @PutMapping("/update/{variationId}")
    public ResponseEntity<VariationDTO> updateVariation(@PathVariable Long variationId, @Valid @RequestBody VariationDTO variationDTO) throws VariationException {
        Variation updatedVariation = variationService.updateVariation(variationId, variationDTO);
        return ResponseEntity.ok(new VariationDTO(updatedVariation));
    }

    @DeleteMapping("/delete/id/{variationId}")
    public void deleteVariation(@PathVariable Long variationId) {
        variationService.deleteVariationById(variationId);
    }
}
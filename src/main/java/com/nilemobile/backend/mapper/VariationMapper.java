package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.VariationDTO;
import com.nilemobile.backend.model.Variation;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VariationMapper {
    Variation toEntity(VariationDTO variationDTO);

    VariationDTO toDto(Variation variation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Variation partialUpdate(VariationDTO variationDTO, @MappingTarget Variation variation);
}
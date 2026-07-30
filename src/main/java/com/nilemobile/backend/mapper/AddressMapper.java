package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.dto.request.AddAddressRequest;
import com.nilemobile.backend.model.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AddressMapper {

    AddressDTO toDto(Address address);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "customer", ignore = true)
    Address requestToEntity(AddAddressRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addressId", ignore = true)
    AddressDTO partialUpdate(AddressDTO addressDTO, @MappingTarget Address address);

    List<AddressDTO> toDtoList(List<Address> addresses);
}

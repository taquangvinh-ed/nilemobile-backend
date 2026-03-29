package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.CustomerDTO;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.RegisterNewCustomerResponseDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    User toEntity(CustomerDTO customerDTO);

    CustomerDTO toDto(User user, Customer customer);

    RegisterNewCustomerResponseDTO toRegisterNewCustomerResponseDTO(User user, Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(CustomerDTO customerDTO, @MappingTarget User user);
}
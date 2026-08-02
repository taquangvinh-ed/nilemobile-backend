package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.RegisterNewCustomerResponseDTO;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;

public interface CustomerService {

    RegisterNewCustomerResponseDTO registerCustomer(CreateNewUserRequest request) ;


}

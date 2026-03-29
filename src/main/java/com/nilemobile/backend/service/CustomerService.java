package com.nilemobile.backend.service;

import com.nilemobile.backend.reponse.RegisterNewCustomerResponseDTO;
import com.nilemobile.backend.request.CreateNewUserRequest;

public interface CustomerService {

    RegisterNewCustomerResponseDTO registerCustomer(CreateNewUserRequest request);


}

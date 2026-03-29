package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.CustomerDTO;
import com.nilemobile.backend.mapper.CustomerMapper;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.RegisterNewCustomerResponseDTO;
import com.nilemobile.backend.repository.CustomerRepository;
import com.nilemobile.backend.request.CreateNewUserRequest;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.service.CustomerService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CartService cartService;

    @Override
    public RegisterNewCustomerResponseDTO registerCustomer(CreateNewUserRequest request){
        User newUser = userService.registerUser(request);
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(request.getFirstName());
        newCustomer.setLastName(request.getLastName());
        newCustomer.setUser(newUser);
        Customer savedCustomer = customerRepository.save(newCustomer);
        cartService.createCart(savedCustomer);
        return customerMapper.toRegisterNewCustomerResponseDTO(newUser, savedCustomer);
    }
}

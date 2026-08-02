package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import com.nilemobile.backend.service.AdminService;
import com.nilemobile.backend.service.AuthenticationService;
import com.nilemobile.backend.service.CustomerService;
import com.nilemobile.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerService customerService;
    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(CreateNewUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if(request.getRoleName().equals("CUSTOMER")){
            customerService.registerCustomer(request);
        }
        if (request.getRoleName().equals("ADMIN")) {
            adminService.registerAdmin(request);
        }
        else {
            throw new IllegalArgumentException("This feature is not implemeted");
        }
    }

    @Override
    public String login(String identifier, String password) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        return jwtService.generateToken(authentication);
    }
}

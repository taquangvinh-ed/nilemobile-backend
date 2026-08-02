package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.AdminDTO;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import com.nilemobile.backend.mapper.AdminMapper;
import com.nilemobile.backend.model.Admin;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.AdminRepository;
import com.nilemobile.backend.service.AdminService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final AdminMapper adminMapper;

    @Override
    public AdminDTO registerAdmin(CreateNewUserRequest request) {
        User newUser = userService.registerUser(request);
        Admin newAdmin = Admin.builder()
                .adminId(newUser.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .user(newUser)
                .build();
        return adminMapper.toDto(newAdmin);
    }
}

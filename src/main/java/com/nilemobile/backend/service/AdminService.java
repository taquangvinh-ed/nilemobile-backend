package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.AdminDTO;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;

public interface AdminService {
    AdminDTO registerAdmin(CreateNewUserRequest request);
}

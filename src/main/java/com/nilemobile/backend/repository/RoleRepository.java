package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Byte> {
}
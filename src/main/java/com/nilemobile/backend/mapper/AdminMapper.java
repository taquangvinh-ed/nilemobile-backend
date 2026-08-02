package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.AdminDTO;
import com.nilemobile.backend.model.Admin;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    Admin toEntity(AdminDTO adminDTO);

    @Mapping(target = "email", source = "admin.user.email")
    AdminDTO toDto(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(AdminDTO adminDTO, @MappingTarget Admin admin);
}
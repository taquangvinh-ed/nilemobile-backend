package com.nilemobile.backend.mapper;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.UserDTO;
import com.nilemobile.backend.request.CreateNewUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true) // Ignore password field during mapping
    User toEntity(CreateNewUserRequest request);
    
    UserDTO toDTO(User user);
}

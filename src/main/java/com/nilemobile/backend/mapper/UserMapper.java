package com.nilemobile.backend.mapper;

import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.reponse.UserDTO;
import com.nilemobile.backend.dto.request.CreateNewUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "pwdHash", ignore = true) // Ignore password field during mapping
    User toEntity(CreateNewUserRequest request);

    @Mapping(target = "roleName", source = "user.role.roleName") // Map role name from User entity to UserDTO
    UserDTO toDTO(User user);

    List<UserDTO> toDTOList(List<User> users);
}

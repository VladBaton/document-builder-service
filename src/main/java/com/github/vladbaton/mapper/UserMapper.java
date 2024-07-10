package com.github.vladbaton.mapper;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.dto.UserDTO;

//@Mapper(componentModel = "cdi")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}

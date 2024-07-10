package com.github.vladbaton.mapper;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.entity.builder.UserBuilder;
import com.github.vladbaton.resource.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@Singleton
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return new UserBuilder()
                .setDirector(userDTO.getDirectorId())
                .setEmail(userDTO.getEmail())
                .setUsername(userDTO.getUsername())
                .setName(userDTO.getName())
                .setPatronymic(userDTO.getPatronymic())
                .setPhone(userDTO.getPhone())
                .setRole(userDTO.getRole())
                .setPassword(userDTO.getPassword())
                .setCreated(userDTO.getCreatedDate())
                .setUpdated(userDTO.getUpdatedDate())
                .setId(userDTO.getUserId())
                .setSurname(userDTO.getSurname())
                .build();
    }
}

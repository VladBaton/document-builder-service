package com.github.vladbaton.resource.dto.builder;

import com.github.vladbaton.resource.dto.UserDTO;

import java.util.Date;

public class UserDTOBuilder {
    private final UserDTO userDTO = new UserDTO();

    public UserDTOBuilder setUserId(final Long userId) {
        userDTO.setUserId(userId);
        return this;
    }

    public UserDTOBuilder setUsername(final String username) {
        userDTO.setUsername(username);
        return this;
    }

    public UserDTOBuilder setEmail(final String email) {
        userDTO.setEmail(email);
        return this;
    }

    public UserDTOBuilder setCreated(final Date created) {
        userDTO.setCreatedDate(created);
        return this;
    }

    public UserDTOBuilder setUpdated(final Date updated) {
        userDTO.setUpdatedDate(updated);
        return this;
    }

    public UserDTOBuilder setPassword(final String password) {
        userDTO.setPassword(password);
        return this;
    }

    public UserDTOBuilder setName(final String name) {
        userDTO.setName(name);
        return this;
    }

    public UserDTOBuilder setSurname(String surname) {
        userDTO.setSurname(surname);
        return this;
    }

    public UserDTOBuilder setPatronymic(final String patronymic) {
        userDTO.setPatronymic(patronymic);
        return this;
    }

    public UserDTOBuilder setPhone(final Long phone) {
        userDTO.setPhone(phone);
        return this;
    }

    public UserDTOBuilder setDirector(final Long directorId) {
        userDTO.setDirectorId(directorId);
        return this;
    }

    public UserDTOBuilder setRole(final String role) {
        userDTO.setRole(role);
        return this;
    }

    public UserDTO build() {
        return userDTO;
    }
}

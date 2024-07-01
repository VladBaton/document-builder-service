package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.dto.UserDTO;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RegistrationRequest {
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

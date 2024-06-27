package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;

import java.util.Set;

//Пользователь получает только username и email
public class UserForUserResponse {
    private String username;
    private String email;

    public UserForUserResponse(User user) {
        setEmail(user.getEmail());
        setUsername(user.getUsername());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.User;

public class UpdateRequest {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

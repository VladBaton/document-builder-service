package com.github.vladbaton.entity.builder;

import com.github.vladbaton.entity.User;

import java.util.Date;

public class UserBuilder {
    private final User user = new User();

    public UserBuilder setId(final Long id) {
        user.setUserId(id);
        return this;
    }

    public UserBuilder setName(final String name) {
        user.setUsername(name);
        return this;
    }

    public UserBuilder setEmail(final String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(final String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(final String role) {
        user.setRole(role);
        return this;
    }

    public UserBuilder setCreated(final Date created) {
        user.setCreatedDate(created);
        return this;
    }

    public UserBuilder setUpdated(final Date updated) {
        user.setUpdatedDate(updated);
        return this;
    }

    public User build()
    {
        return user;
    }
}

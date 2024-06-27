package com.github.vladbaton.exception;

public class UserNotFoundByUsernameException extends IllegalArgumentException {
    private final String username;

    public UserNotFoundByUsernameException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

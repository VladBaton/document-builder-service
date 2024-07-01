package com.github.vladbaton.resource.dto;

import com.github.vladbaton.constraint.Username;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.PasswordType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RequestScoped
public class UserDTO {
    @NotBlank
    @Username
    private String username;

    @com.github.vladbaton.constraint.Password
    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

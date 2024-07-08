package com.github.vladbaton.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vladbaton.constraint.Username;
import com.github.vladbaton.entity.User;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@RequestScoped
public class UserDTO {
    @JsonIgnore
    private Long userId;

    @NotNull
    @NotBlank
    @Username
    private String username;

    @com.github.vladbaton.constraint.Password(message = "Дружок-пирожок, тобой был введён неправильный пароль: ")
    @NotBlank
    private String password;

    @NotBlank
    @Email(message = "Дружок-пирожок, тобой был введён неправильный email")
    private String email;

    @JsonIgnoreProperties
    private Date createdDate;

    @JsonIgnoreProperties
    private Date updatedDate;

    public UserDTO() {

    }

    public UserDTO(User user) {
        setUserId(user.getUserId());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setEmail(user.getEmail());
        setCreatedDate(user.getCreatedDate());
        setUpdatedDate(user.getUpdatedDate());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnoreProperties
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}

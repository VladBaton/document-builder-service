package com.github.vladbaton.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vladbaton.constraint.PhoneNumber;
import com.github.vladbaton.constraint.Username;
import com.github.vladbaton.entity.User;
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

    @Username
    private String name;

    @Username
    private String surname;

    @Username
    private String patronymic;

    @PhoneNumber
    private Long phone;

    private Long directorId;

    private String role;

    public UserDTO() {

    }

    public UserDTO(User user) {
        setUserId(user.getUserId());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setEmail(user.getEmail());
        setCreatedDate(user.getCreatedDate());
        setUpdatedDate(user.getUpdatedDate());
        setName(user.getName());
        setSurname(user.getSurname());
        setPatronymic(user.getPatronymic());
        setPhone(user.getPhone());
        setDirectorId(user.getDirector());
        setRole(user.getRole());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

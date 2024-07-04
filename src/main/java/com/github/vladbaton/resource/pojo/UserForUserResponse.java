package com.github.vladbaton.resource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vladbaton.entity.Doc;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.dto.UserDTO;

import java.util.Date;
import java.util.Set;

//Пользователь получает только username и email
public class UserForUserResponse extends UserDTO {
    public UserForUserResponse(User user) {
        super(user);
    }

    @JsonProperty
    @Override
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @JsonProperty
    @Override
    public Date getUpdatedDate() {
        return super.getUpdatedDate();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}

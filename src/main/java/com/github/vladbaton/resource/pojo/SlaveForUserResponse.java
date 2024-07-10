package com.github.vladbaton.resource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.dto.UserDTO;

public class SlaveForUserResponse extends UserDTO {
    public SlaveForUserResponse(User user) {
        super(user);
    }

    @JsonIgnore
    @Override
    public Long getDirectorId() {
        return super.getDirectorId();
    }

    @JsonIgnore
    @Override
    public String getRole() {
        return super.getRole();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}

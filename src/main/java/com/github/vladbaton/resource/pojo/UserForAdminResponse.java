package com.github.vladbaton.resource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.dto.UserDTO;

public class UserForAdminResponse extends UserDTO {
    private Integer userFilesCount;

    public UserForAdminResponse(User user) {
        super(user);
        setUserFilesCount(user.getDocs().size());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

    public Integer getUserFilesCount() {
        return userFilesCount;
    }

    public void setUserFilesCount(Integer userFilesCount) {
        this.userFilesCount = userFilesCount;
    }
}

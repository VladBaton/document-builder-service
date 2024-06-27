package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.User;

public class UserForAdminResponse extends UserForUserResponse {
    private Long userId;
    private Integer userFilesCount;

    public UserForAdminResponse(User user) {
        super(user);
        setUserId(user.getUserId());
        setUserFilesCount(user.getDocs().size());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserFilesCount() {
        return userFilesCount;
    }

    public void setUserFilesCount(Integer userFilesCount) {
        this.userFilesCount = userFilesCount;
    }
}

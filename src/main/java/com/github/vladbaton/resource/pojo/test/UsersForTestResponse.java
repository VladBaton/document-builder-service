package com.github.vladbaton.resource.pojo.test;

import com.github.vladbaton.entity.User;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.List;

public class UsersForTestResponse {
    List<UserForTestResponse> userForTestResponseList = new ArrayList<>();

    public UsersForTestResponse(List<User> users) {
        users.forEach(user -> userForTestResponseList.add(new UserForTestResponse(user)));
    }

    public List<UserForTestResponse> getUserForTestResponseList() {
        return userForTestResponseList;
    }

    public void setUserForTestResponseList(List<UserForTestResponse> userForTestResponseList) {
        this.userForTestResponseList = userForTestResponseList;
    }
}

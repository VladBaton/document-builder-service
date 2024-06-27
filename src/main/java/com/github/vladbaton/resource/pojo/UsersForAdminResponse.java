package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UsersForAdminResponse {
    private List<UserForAdminResponse> userForAdminResponses = new ArrayList<>();

    public UsersForAdminResponse(List<User> users) {
        for (User user : users) {
            userForAdminResponses.add(new UserForAdminResponse(user));
        }
    }

    public List<UserForAdminResponse> getUserForAdminResponses() {
        return userForAdminResponses;
    }

    public void setUserForAdminResponses(List<UserForAdminResponse> userForAdminResponses) {
        this.userForAdminResponses = userForAdminResponses;
    }
}

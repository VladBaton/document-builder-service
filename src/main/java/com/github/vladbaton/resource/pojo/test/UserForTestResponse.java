package com.github.vladbaton.resource.pojo.test;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.resource.pojo.DocForUserResponse;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.List;

public class UserForTestResponse {
    private String username;
    private String email;
    private String password;
    List<DocForUserResponse> docs = new ArrayList<>();

    UserForTestResponse(User user) {
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        user.getDocs().stream().map(DocForUserResponse::new).forEach(docs::add);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DocForUserResponse> getDocs() {
        return docs;
    }

    public void setDocs(List<DocForUserResponse> docs) {
        this.docs = docs;
    }
}

package com.github.vladbaton.entity;

import com.github.vladbaton.constraint.Username;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "USERS")
@UserDefinition
public class User extends PanacheEntityBase {
    @Column(name = "USERID")
    @Id
    @GeneratedValue
    private Long userId;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @NotBlank
    @io.quarkus.security.jpa.Username
    @Username
    private String username;

    @Column(name = "USERPASSWORD", nullable = false)
    @NotBlank
    @Password(PasswordType.CLEAR)
    @com.github.vladbaton.constraint.Password
    private String password;

    @Column(name = "USEREMAIL", nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name = "USERROLE")
    @Roles
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Doc> docs;


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Doc> getDocs() {
        return docs;
    }

    public void setDocs(Set<Doc> docs) {
        this.docs = docs;
    }
}

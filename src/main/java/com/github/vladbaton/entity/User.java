package com.github.vladbaton.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.PasswordType;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USERS", indexes = {@Index(name = "idx_randomstuff", columnList = "randomStuff")})
@UserDefinition
public class User extends PanacheEntityBase {
    @Column(name = "USERID")
    @Id
    @GeneratedValue
    private Long userId;

    @io.quarkus.security.jpa.Username
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Password(value = PasswordType.MCF)
    @Column(name = "USERPASSWORD", nullable = false)
    private String password;

    @Column(name = "USEREMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USERROLE")
    @Roles
    private String role;

    private Long randomStuff;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Doc> docs;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedDate;

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
        this.password = BcryptUtil.bcryptHash(password);
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

    public Long getRandomStuff() {
        return randomStuff;
    }

    public void setRandomStuff(Long randomStuff) {
        this.randomStuff = randomStuff;
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

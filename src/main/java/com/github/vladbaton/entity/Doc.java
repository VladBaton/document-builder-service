package com.github.vladbaton.entity;

import com.github.vladbaton.constraint.DoxcFileExtension;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name =  "DOCS")
public class Doc extends PanacheEntity {
    @ManyToOne
    private User user;

    @Column(name = "fileReference", nullable = false)
    @DoxcFileExtension
    private String fileReference;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date lastUpdatedDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}

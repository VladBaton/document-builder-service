package com.github.vladbaton.resource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.vladbaton.entity.Doc;
import jakarta.enterprise.context.RequestScoped;

import java.util.Date;

public class DocForUserResponse {
    String filename;
    Date createdDate;
    Date lastUpdatedDate;

    public DocForUserResponse(Doc doc) {
        filename = doc.getFileReference().substring(doc.getFileReference().lastIndexOf("\\") + 1);
        createdDate = doc.getCreatedDate();
        lastUpdatedDate = doc.getLastUpdatedDate();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

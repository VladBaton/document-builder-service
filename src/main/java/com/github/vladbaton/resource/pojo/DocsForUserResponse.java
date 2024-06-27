package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.entity.Doc;
import jakarta.enterprise.context.RequestScoped;

import java.util.HashSet;
import java.util.Set;

public class DocsForUserResponse {
    private Set<DocForUserResponse> docsForUserResponses = new HashSet<>();

    public DocsForUserResponse(Set<Doc> docs) {
        for(Doc doc : docs) {
            docsForUserResponses.add(new DocForUserResponse(doc));
        }
    }

    public Set<DocForUserResponse> getDocsForUserResponses() {
        return docsForUserResponses;
    }

    public void setDocsForUserResponses(Set<DocForUserResponse> docsForUserResponses) {
        this.docsForUserResponses = docsForUserResponses;
    }
}

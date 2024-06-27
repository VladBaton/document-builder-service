package com.github.vladbaton.resource.pojo;

import java.util.List;
import java.util.Map;

public class BuildDocumentRequest {
    private List<BuildDocumentRequestEntry> targetsReplacements;

    public List<BuildDocumentRequestEntry> getTargetsReplacements() {
        return targetsReplacements;
    }

    public void setTargetsReplacements(List<BuildDocumentRequestEntry> targetsReplacements) {
        this.targetsReplacements = targetsReplacements;
    }
}

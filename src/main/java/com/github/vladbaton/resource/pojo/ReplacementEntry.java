package com.github.vladbaton.resource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ReplacementEntry {
    @JsonIgnore
    private XWPFRun run;
    private String target;
    private String replacement;

    public ReplacementEntry(XWPFRun run, String target, String replacement) {
        this.run = run;
        this.target = target;
        this.replacement = replacement;
    }

    public XWPFRun getRun() {
        return run;
    }

    public void setRun(XWPFRun run) {
        this.run = run;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}

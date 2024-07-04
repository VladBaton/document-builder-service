package com.github.vladbaton.resource.dto;

import com.github.vladbaton.constraint.FieldExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SortByDTO {
    @NotBlank
    @NotNull
    @FieldExists
    private String field;

    @NotNull
    private SortOrder order;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }
}

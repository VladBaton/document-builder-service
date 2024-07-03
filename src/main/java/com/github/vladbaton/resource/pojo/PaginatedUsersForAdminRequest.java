package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.constraint.NotNegative;
import com.github.vladbaton.resource.dto.sortByDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class PaginatedUsersForAdminRequest {
    @Positive
    private int pageSize;

    @NotNegative
    private int page;

    @Valid
    private List<sortByDTO> sortBy;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<sortByDTO> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<sortByDTO> sortBy) {
        this.sortBy = sortBy;
    }
}

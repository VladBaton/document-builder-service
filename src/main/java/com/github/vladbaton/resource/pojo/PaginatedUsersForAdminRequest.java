package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.constraint.NotNegative;
import com.github.vladbaton.resource.dto.SortByDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class PaginatedUsersForAdminRequest {
    @Positive
    private int pageSize;

    @NotNegative
    private int page;

    @Valid
    private List<SortByDTO> sortBy;

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

    public List<SortByDTO> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortByDTO> sortBy) {
        this.sortBy = sortBy;
    }
}

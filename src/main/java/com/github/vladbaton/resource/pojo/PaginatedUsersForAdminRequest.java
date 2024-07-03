package com.github.vladbaton.resource.pojo;

import com.github.vladbaton.resource.dto.sortByDTO;

import java.util.List;

public class PaginatedUsersForAdminRequest {
    private Integer pageSize;
    private Integer page;
    private List<sortByDTO> sortBy;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<sortByDTO> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<sortByDTO> sortBy) {
        this.sortBy = sortBy;
    }
}

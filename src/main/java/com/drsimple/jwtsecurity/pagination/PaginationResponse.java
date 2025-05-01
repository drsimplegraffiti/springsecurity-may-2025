package com.drsimple.jwtsecurity.pagination;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int currentPage;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginationResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        this.currentPage = page.getNumber() + 1;  // make it 1-based
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}
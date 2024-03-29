package com.andaily.domain.shared.paginated;

/**
 * @author Shengzhao Li
 */
public enum PaginatedSort {
    ASC("asc"),
    DESC("desc");

    private String label;

    PaginatedSort(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
package com.tjeuvreeburg.flightapi.base.responses;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginatedResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean last, boolean first) {

    public PaginatedResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

    public static <T> PaginatedResponse<T> from(Page<T> page) {
        return new PaginatedResponse<>(page);
    }
}
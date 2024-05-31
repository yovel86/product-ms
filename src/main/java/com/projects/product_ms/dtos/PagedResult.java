package com.projects.product_ms.dtos;

import java.util.List;

public record PagedResult<T> (
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious
) { }

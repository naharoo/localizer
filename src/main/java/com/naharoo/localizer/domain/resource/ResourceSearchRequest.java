package com.naharoo.localizer.domain.resource;

import com.naharoo.localizer.domain.SortOrder;
import lombok.Data;

import java.util.Objects;

@Data
public class ResourceSearchRequest {

    private final String query;
    private final String localeQuery;
    private final int from;
    private final int size;
    private final ResourceSortField sortField;
    private final SortOrder sortOrder;

    public ResourceSearchRequest(
        final String query,
        final String localeQuery,
        final Integer from,
        final Integer size,
        final ResourceSortField sortField,
        final SortOrder sortOrder
    ) {
        this.query = query;
        this.localeQuery = localeQuery;
        this.from = Objects.requireNonNull(from);
        this.size = Objects.requireNonNull(size);
        this.sortField = Objects.requireNonNull(sortField);
        this.sortOrder = Objects.requireNonNull(sortOrder);
    }
}

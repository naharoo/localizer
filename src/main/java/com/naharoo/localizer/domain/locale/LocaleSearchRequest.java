package com.naharoo.localizer.domain.locale;

import com.naharoo.localizer.domain.SortOrder;
import lombok.Data;

import java.util.Objects;

@Data
public class LocaleSearchRequest {

    private final String query;
    private final int from;
    private final int size;
    private final LocaleSortField sortField;
    private final SortOrder sortOrder;

    public LocaleSearchRequest(
        final String query,
        final Integer from,
        final Integer size,
        final LocaleSortField sortField,
        final SortOrder sortOrder
    ) {
        this.query = query;
        this.from = Objects.requireNonNull(from);
        this.size = Objects.requireNonNull(size);
        this.sortField = Objects.requireNonNull(sortField);
        this.sortOrder = Objects.requireNonNull(sortOrder);
    }
}

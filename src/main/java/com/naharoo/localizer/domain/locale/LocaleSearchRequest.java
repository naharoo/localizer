package com.naharoo.localizer.domain.locale;

import com.naharoo.localizer.domain.SortOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class LocaleSearchRequest {

    private final int from;
    private final int size;
    private final LocaleSortField sortField;
    private final SortOrder sortOrder;

    public LocaleSearchRequest(
        final Integer from,
        final Integer size,
        final LocaleSortField sortField,
        final SortOrder sortOrder
    ) {
        this.from = Objects.requireNonNull(from);
        this.size = Objects.requireNonNull(size);
        this.sortField = Objects.requireNonNull(sortField);
        this.sortOrder = Objects.requireNonNull(sortOrder);
    }

    public int getFrom() {
        return from;
    }

    public int getSize() {
        return size;
    }

    public LocaleSortField getSortField() {
        return sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LocaleSearchRequest that = (LocaleSearchRequest) o;

        return new EqualsBuilder()
            .append(from, that.from)
            .append(size, that.size)
            .append(sortField, that.sortField)
            .append(sortOrder, that.sortOrder)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(from)
            .append(size)
            .append(sortField)
            .append(sortOrder)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("from", from)
            .append("size", size)
            .append("sortField", sortField)
            .append("sortOrder", sortOrder)
            .toString();
    }
}

package com.naharoo.localizer.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class GenericListResponse<T> {

    private final List<T> items;
    private final long totalItems;

    public GenericListResponse(final List<T> items, final long totalItems) {
        this.items = items;
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public long getTotalItems() {
        return totalItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GenericListResponse<?> that = (GenericListResponse<?>) o;

        return new EqualsBuilder()
            .append(totalItems, that.totalItems)
            .append(items, that.items)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(items)
            .append(totalItems)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("items", items)
            .append("totalItems", totalItems)
            .toString();
    }
}

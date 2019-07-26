package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.locale.LocaleSortField;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.Min;

public class LocaleSearchRequestDto {

    public static final int DEFAULT_FROM = 0;
    public static final int DEFAULT_SIZE = 20;
    public static final LocaleSortField DEFAULT_SORT_FIELD = LocaleSortField.ID;
    public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

    @Min(0)
    private final Integer from;

    @Min(0)
    private final Integer size;

    private final LocaleSortField sortField;
    private final SortOrder sortOrder;

    @JsonCreator
    public LocaleSearchRequestDto(
        @JsonProperty("from") final Integer from,
        @JsonProperty("size") final Integer size,
        @JsonProperty("sortField") final LocaleSortField sortField,
        @JsonProperty("sortOrder") final SortOrder sortOrder
    ) {
        this.from = from == null ? DEFAULT_FROM : from;
        this.size = size == null ? DEFAULT_SIZE : size;
        this.sortField = sortField == null ? DEFAULT_SORT_FIELD : sortField;
        this.sortOrder = sortOrder == null ? DEFAULT_SORT_ORDER : sortOrder;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getSize() {
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

        LocaleSearchRequestDto that = (LocaleSearchRequestDto) o;

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
}

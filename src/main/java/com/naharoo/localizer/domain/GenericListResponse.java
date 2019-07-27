package com.naharoo.localizer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@ApiModel(description = "Class representing paginated Search response")
public class GenericListResponse<T> {

    @ApiModelProperty(
        notes = "Search response items container.",
        example = "[{}]",
        required = true,
        position = 0
    )
    private final List<T> items;

    @ApiModelProperty(
        notes = "Total number of items that could be fetched using applied filter.",
        example = "50",
        required = true,
        position = 1
    )
    private final long totalItems;

    @JsonCreator
    public GenericListResponse(
        @JsonProperty("items") final List<T> items,
        @JsonProperty("totalItems") final long totalItems
    ) {
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

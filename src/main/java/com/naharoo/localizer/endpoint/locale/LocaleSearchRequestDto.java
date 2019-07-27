package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.locale.LocaleSortField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.Min;

@ApiModel(description = "Class containing all necessary data for Locale search")
public class LocaleSearchRequestDto {

    private static final int DEFAULT_FROM = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final LocaleSortField DEFAULT_SORT_FIELD = LocaleSortField.ID;
    private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

    @Min(0)
    @ApiModelProperty(
        notes = "Specifies from which index should sorted Locale list be fetched. Default value 0 will be picked if not provided.",
        example = "0",
        position = 0
    )
    private final Integer from;

    @Min(1)
    @ApiModelProperty(
        notes = "Specifies how many Locales should  be fetched. Default value 20 will be picked if not provided.",
        example = "20",
        position = 1
    )
    private final Integer size;

    @ApiModelProperty(
        notes = "Specifies which Locale field will be used for sorting. Default value ID will be picked if not provided.",
        example = "20",
        position = 2
    )
    private final LocaleSortField sortField;

    @ApiModelProperty(
        notes = "Specifies in which order Locale should b sorting. Default value ASC will be picked if not provided.",
        example = "20",
        position = 2
    )
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

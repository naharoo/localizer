package com.naharoo.localizer.endpoint.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.resource.ResourceSortField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "Class containing all necessary data for Resource search")
public class ResourceSearchRequestDto {

    private static final int DEFAULT_FROM = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final ResourceSortField DEFAULT_SORT_FIELD = ResourceSortField.ID;
    private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

    @ApiModelProperty(
        notes = "Query string which will be searched in Resource' key field. No filtering by query will be applied if null is provided.",
        example = "doc",
        position = 0
    )
    private final String query;

    @ApiModelProperty(
        notes = "Query string which will be searched in Locales' key and name fields. No filtering by query will be applied if null is provided.",
        example = "en-US",
        position = 1
    )
    private final String localeQuery;

    @Min(0)
    @ApiModelProperty(
        notes = "Specifies from which index should sorted Resource list be fetched. Default value 0 will be picked if not provided.",
        example = "0",
        position = 2
    )
    private final Integer from;

    @Min(1)
    @ApiModelProperty(
        notes = "Specifies how many Resource should  be fetched. Default value 20 will be picked if not provided.",
        example = "20",
        position = 3
    )
    private final Integer size;

    @ApiModelProperty(
        notes = "Specifies which Resource field will be used for sorting. Default value ID will be picked if not provided.",
        example = "ID",
        position = 4
    )
    private final ResourceSortField sortField;

    @ApiModelProperty(
        notes = "Specifies in which order Resource should be sorting. Default value ASC will be picked if not provided.",
        example = "ASC",
        position = 5
    )
    private final SortOrder sortOrder;

    @JsonCreator
    public ResourceSearchRequestDto(
        @JsonProperty("query") final String query,
        @JsonProperty("localeQuery") final String localeQuery,
        @JsonProperty("from") final Integer from,
        @JsonProperty("size") final Integer size,
        @JsonProperty("sortField") final ResourceSortField sortField,
        @JsonProperty("sortOrder") final SortOrder sortOrder
    ) {
        this.query = query;
        this.localeQuery = localeQuery;
        this.from = from == null ? DEFAULT_FROM : from;
        this.size = size == null ? DEFAULT_SIZE : size;
        this.sortField = sortField == null ? DEFAULT_SORT_FIELD : sortField;
        this.sortOrder = sortOrder == null ? DEFAULT_SORT_ORDER : sortOrder;
    }
}

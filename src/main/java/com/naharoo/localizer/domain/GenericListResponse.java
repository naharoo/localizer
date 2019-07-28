package com.naharoo.localizer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
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
}

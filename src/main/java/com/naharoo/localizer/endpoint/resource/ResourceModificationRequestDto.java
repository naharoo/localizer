package com.naharoo.localizer.endpoint.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "Class containing all new values for Resource modification")
public class ResourceModificationRequestDto {

    @NotBlank
    @ApiModelProperty(
        notes = "Unique identifier of the resource. No two resources can have the same id.",
        example = "cbd5cf7a-b8cc-41ed-9998-c12111df81e3",
        required = true,
        position = 0
    )
    private final String id;

    @NotBlank
    @ApiModelProperty(
        notes = "The new key of the Resource which should stay unique together with Resource's Locale. The older key will be updated with provided one.",
        example = "document",
        required = true,
        position = 1
    )
    private final String key;

    @NotBlank
    @ApiModelProperty(
        notes = "The new localized value of the Resource. The older value will be updated with provided one.",
        example = "Document",
        required = true,
        position = 2
    )
    private final String value;

    @JsonCreator
    public ResourceModificationRequestDto(
        @JsonProperty("id") final String id,
        @JsonProperty("key") final String key,
        @JsonProperty("value") final String value
    ) {
        this.id = id;
        this.key = key;
        this.value = value;
    }
}
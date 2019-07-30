package com.naharoo.localizer.endpoint.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "Class containing all necessary data for Resource creation")
public class ResourceCreationRequestDto {

    @NotBlank
    @ApiModelProperty(
        notes = "Key of the Resource. Together with Locale is composite unique identifier of Resource. No two resources can have the same key + locale pair.",
        example = "document",
        required = true,
        position = 0
    )
    private final String key;

    @NotBlank
    @ApiModelProperty(
        notes = "LocaleId of Resource. Together with Key is composite unique identifier of Resource. No two resources can have the same key + locale pair.",
        example = "4370dff7-a73a-47a3-9672-fa2379deae5b",
        required = true,
        position = 1
    )
    private final String localeId;

    @NotBlank
    @ApiModelProperty(
        notes = "Localized value of the Resource.",
        example = "Document",
        required = true,
        position = 2
    )
    private final String value;

    @JsonCreator
    public ResourceCreationRequestDto(
        @JsonProperty("key") final String key,
        @JsonProperty("localeId") final String localeId,
        @JsonProperty("value") final String value
    ) {
        this.key = key;
        this.localeId = localeId;
        this.value = value;
    }
}
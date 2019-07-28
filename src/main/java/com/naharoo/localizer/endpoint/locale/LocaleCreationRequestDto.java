package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "Class containing all necessary data for Locale creation")
public class LocaleCreationRequestDto {

    @NotBlank
    @ApiModelProperty(
        notes = "Unique key of the Locale. No two locales can have the same key.",
        example = "en-US",
        required = true,
        position = 0
    )
    private final String key;

    @NotBlank
    @ApiModelProperty(
        notes = "Name of the Locale.",
        example = "American English",
        required = true,
        position = 1
    )
    private final String name;

    @JsonCreator
    public LocaleCreationRequestDto(
        @JsonProperty("key") final String key,
        @JsonProperty("name") final String name
    ) {
        this.key = key;
        this.name = name;
    }
}
package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "Contains all new values for Locale modification")
public class LocaleModificationRequestDto {

    @NotBlank
    @ApiModelProperty(
        notes = "Unique identifier of the locale. No two locales can have the same id. " +
            "Used for identifying the Locale which will be updated.",
        example = "cbd5cf7a-b8cc-41ed-9998-c12111df81e3",
        required = true,
        position = 0
    )
    private final String id;

    @NotBlank
    @ApiModelProperty(
        notes = "The new unique key of the Locale. The older key will be updated with provided one.",
        example = "en-US",
        required = true,
        position = 1
    )
    private final String key;

    @NotBlank
    @ApiModelProperty(
        notes = "New name of the Locale. The older name will be updated with provided one.",
        example = "American English",
        required = true,
        position = 2
    )
    private final String name;

    @JsonCreator
    public LocaleModificationRequestDto(
        @JsonProperty("id") final String id,
        @JsonProperty("key") final String key,
        @JsonProperty("name") final String name
    ) {
        this.id = id;
        this.key = key;
        this.name = name;
    }
}
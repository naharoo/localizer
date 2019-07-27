package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

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

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LocaleCreationRequestDto that = (LocaleCreationRequestDto) o;

        return new EqualsBuilder()
            .append(key, that.key)
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(key)
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("key", key)
            .append("name", name)
            .toString();
    }
}
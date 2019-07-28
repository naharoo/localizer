package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Class containing all new values for Locale modification")
public class LocaleModificationRequestDto {

    @NotBlank
    @ApiModelProperty(
        notes = "Unique identifier of the locale. No two locales can have the same id. Used for identifying the Locale which will be updated.",
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

    public String getId() {
        return id;
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

        LocaleModificationRequestDto that = (LocaleModificationRequestDto) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(key, that.key)
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(key)
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("key", key)
            .append("name", name)
            .toString();
    }
}
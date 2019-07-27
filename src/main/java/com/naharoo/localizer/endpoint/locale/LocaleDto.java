package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

@ApiModel(description = "Class representing a Locale in the application")
public class LocaleDto {

    @ApiModelProperty(
        notes = "Unique identifier of the locale. No two locales can have the same id.",
        example = "cbd5cf7a-b8cc-41ed-9998-c12111df81e3",
        required = true,
        position = 0
    )
    private final String id;

    @ApiModelProperty(
        notes = "Unique key of the Locale. No two locales can have the same key.",
        example = "en-US",
        required = true,
        position = 1
    )
    private final String key;

    @ApiModelProperty(
        notes = "Name of the Locale.",
        example = "American English",
        required = true,
        position = 2
    )
    private final String name;

    @ApiModelProperty(
        notes = "Creation date of the Locale.",
        example = "2019-07-26T14:21:26.299831",
        required = true,
        position = 3
    )
    private final LocalDateTime created;

    @ApiModelProperty(
        notes = "Last modification date of the Locale.",
        example = "2019-07-26T14:21:26.299831",
        required = true,
        position = 4
    )
    private final LocalDateTime updated;

    @ApiModelProperty(
        notes = "Deletion date of the Locale.",
        example = "2019-07-26T14:21:26.299831",
        position = 5
    )
    private final LocalDateTime deleted;

    @JsonCreator
    public LocaleDto(
        @JsonProperty("id") final String id,
        @JsonProperty("key") final String key,
        @JsonProperty("name") final String name,
        @JsonProperty("created") final LocalDateTime created,
        @JsonProperty("updated") final LocalDateTime updated,
        @JsonProperty("deleted") final LocalDateTime deleted
    ) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LocaleDto localeDto = (LocaleDto) o;

        return new EqualsBuilder()
            .append(id, localeDto.id)
            .append(key, localeDto.key)
            .append(name, localeDto.name)
            .append(created, localeDto.created)
            .append(updated, localeDto.updated)
            .append(deleted, localeDto.deleted)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(key)
            .append(name)
            .append(created)
            .append(updated)
            .append(deleted)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("key", key)
            .append("name", name)
            .append("created", created)
            .append("updated", updated)
            .append("deleted", deleted)
            .toString();
    }
}
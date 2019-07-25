package com.naharoo.localizer.endpoint.locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class LocaleDto {

    private final String id;
    private final String key;
    private final String name;
    private final LocalDateTime created;
    private final LocalDateTime updated;
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
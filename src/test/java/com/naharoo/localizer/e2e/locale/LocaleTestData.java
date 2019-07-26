package com.naharoo.localizer.e2e.locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LocaleTestData {

    private final String id;
    private final String key;
    private final String name;
    private final boolean deleted;

    public LocaleTestData(final String id, final String key, final String name, final boolean deleted) {
        this.id = id;
        this.key = key;
        this.name = name;
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

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        LocaleTestData that = (LocaleTestData) object;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(key, that.key)
            .append(name, that.name)
            .append(deleted, that.deleted)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(key)
            .append(name)
            .append(deleted)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("key", key)
            .append("name", name)
            .append("deleted", deleted)
            .toString();
    }
}

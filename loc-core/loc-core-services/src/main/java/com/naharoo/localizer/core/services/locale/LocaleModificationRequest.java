package com.naharoo.localizer.core.services.locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LocaleModificationRequest {

    private final String key;
    private final String name;

    public LocaleModificationRequest(final String key, final String name) {
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

        LocaleModificationRequest that = (LocaleModificationRequest) o;

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

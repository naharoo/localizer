package com.naharoo.localizer.endpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public final class LocalizerApiError {

    private final int status;
    private final List<String> messages;

    @JsonCreator
    public LocalizerApiError(
        @JsonProperty("status") final int status,
        @JsonProperty("messages") final List<String> messages
    ) {
        this.status = status;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        LocalizerApiError that = (LocalizerApiError) object;

        return new EqualsBuilder()
            .append(status, that.status)
            .append(messages, that.messages)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(status)
            .append(messages)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("status", status)
            .append("messages", messages)
            .toString();
    }
}

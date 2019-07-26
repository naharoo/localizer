package com.naharoo.localizer.testutils.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class JsonWrapper {

    private final String json;
    private final JsonNode jsonNode;

    public JsonWrapper(final String resourcePath) {
        final ClassPathResource resource = new ClassPathResource(resourcePath);
        try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            this.json = FileCopyUtils.copyToString(reader);
            this.jsonNode = new ObjectMapper().readTree(this.json);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getJson() {
        return json;
    }

    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public Long getLong(final String path) {
        return this.jsonNode.path(path).asLong();
    }

    public Integer getInt(final String path) {
        return this.jsonNode.path(path).asInt();
    }

    public String getString(final String path) {
        return this.jsonNode.path(path).asText();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        JsonWrapper that = (JsonWrapper) object;

        return new EqualsBuilder()
            .append(json, that.json)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(json)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("json", json)
            .toString();
    }
}

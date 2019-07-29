package com.naharoo.localizer.domain.resource;

import lombok.Data;

import java.util.Objects;

@Data
public class ResourceModificationRequest {

    private final String id;
    private final String key;
    private final String value;

    public ResourceModificationRequest(final String id, final String key, final String value) {
        this.id = Objects.requireNonNull(id);
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }
}

package com.naharoo.localizer.domain.resource;

import lombok.Data;

import java.util.Objects;

@Data
public class ResourceCreationRequest {

    private final String key;
    private final String localeId;
    private final String value;

    public ResourceCreationRequest(final String key, final String localeId, final String value) {
        this.key = Objects.requireNonNull(key);
        this.localeId = Objects.requireNonNull(localeId);
        this.value = Objects.requireNonNull(value);
    }
}

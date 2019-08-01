package com.naharoo.localizer.domain.locale;

import lombok.Data;

import java.util.Objects;

@Data
public class LocaleCreationRequest {

    private final String key;
    private final String name;

    public LocaleCreationRequest(final String key, final String name) {
        this.key = Objects.requireNonNull(key);
        this.name = Objects.requireNonNull(name);
    }
}
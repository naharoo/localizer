package com.naharoo.localizer.domain.locale;

import lombok.Data;

import java.util.Objects;

@Data
public class LocaleModificationRequest {

    private final String id;
    private final String key;
    private final String name;

    public LocaleModificationRequest(final String id, final String key, final String name) {
        this.id = Objects.requireNonNull(id);
        this.key = Objects.requireNonNull(key);
        this.name = Objects.requireNonNull(name);
    }
}
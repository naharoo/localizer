package com.naharoo.localizer.domain.locale;

import lombok.Data;

@Data
public class LocaleModificationRequest {

    private final String id;
    private final String key;
    private final String name;

    public LocaleModificationRequest(final String id, final String key, final String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }
}
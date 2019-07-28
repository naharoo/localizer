package com.naharoo.localizer.domain.locale;

import lombok.Data;

@Data
public class LocaleCreationRequest {

    private final String key;
    private final String name;

    public LocaleCreationRequest(final String key, final String name) {
        this.key = key;
        this.name = name;
    }
}
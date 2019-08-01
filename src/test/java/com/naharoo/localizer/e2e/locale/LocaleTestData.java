package com.naharoo.localizer.e2e.locale;

import lombok.Data;

@Data
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
}

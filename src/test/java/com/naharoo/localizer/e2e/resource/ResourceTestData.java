package com.naharoo.localizer.e2e.resource;

import com.naharoo.localizer.e2e.locale.LocaleTestData;
import lombok.Data;

@Data
public class ResourceTestData {

    private final String id;
    private final String key;
    private final LocaleTestData locale;
    private final String value;
    private final boolean deleted;

    public ResourceTestData(
        final String id,
        final String key,
        final LocaleTestData locale,
        final String value,
        final boolean deleted
    ) {
        this.id = id;
        this.key = key;
        this.locale = locale;
        this.value = value;
        this.deleted = deleted;
    }
}

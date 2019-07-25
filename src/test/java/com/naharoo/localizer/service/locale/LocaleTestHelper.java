package com.naharoo.localizer.service.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public final class LocaleTestHelper {

    private LocaleTestHelper() {
        throw new IllegalAccessError();
    }

    public static Locale createRandomLocale() {
        return createLocale(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    public static Locale createLocale(
        final String id,
        final String key,
        final String name,
        final LocalDateTime created,
        final LocalDateTime updated,
        final LocalDateTime deleted
    ) {
        final Locale locale = new Locale();
        locale.setId(id);
        locale.setKey(key);
        locale.setName(name);
        locale.setCreated(created);
        locale.setUpdated(updated);
        locale.setDeleted(deleted);
        return locale;
    }

    public static LocaleCreationRequest createRandomLocaleCreationRequest() {
        return createLocaleCreationRequest(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static LocaleCreationRequest createLocaleCreationRequest(final String key, final String name) {
        return new LocaleCreationRequest(key, name);
    }
}
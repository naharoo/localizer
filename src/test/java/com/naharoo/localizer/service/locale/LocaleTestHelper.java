package com.naharoo.localizer.service.locale;

import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.locale.*;
import com.naharoo.localizer.endpoint.locale.LocaleCreationRequestDto;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import com.naharoo.localizer.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.endpoint.locale.LocaleSearchRequestDto;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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

    public static LocaleDto createRandomLocaleDto() {
        return createLocaleDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    public static LocaleDto createLocaleDto(
        final String id,
        final String key,
        final String name,
        final LocalDateTime created,
        final LocalDateTime updated,
        final LocalDateTime deleted
    ) {
        return new LocaleDto(id, key, name, created, updated, deleted);
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

    public static LocaleCreationRequestDto createRandomLocaleCreationRequestDto() {
        return createLocaleCreationRequestDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static LocaleCreationRequestDto createLocaleCreationRequestDto(final String key, final String name) {
        return new LocaleCreationRequestDto(key, name);
    }

    public static LocaleSearchRequestDto createRandomLocaleSearchRequestDto() {
        return createLocaleSearchRequestDto(
            UUID.randomUUID().toString(),
            ThreadLocalRandom.current().nextInt(),
            ThreadLocalRandom.current().nextInt(),
            getRandomLocaleSorField(),
            SortOrder.ASC
        );
    }

    public static LocaleSearchRequestDto createLocaleSearchRequestDto(
        final String query,
        final Integer from,
        final Integer size,
        final LocaleSortField sortField,
        final SortOrder sortOrder
    ) {
        return new LocaleSearchRequestDto(query, from, size, sortField, sortOrder);
    }

    public static LocaleSearchRequest createRandomLocaleSearchRequest() {
        return createLocaleSearchRequest(
            UUID.randomUUID().toString(),
            0,
            20,
            getRandomLocaleSorField(),
            SortOrder.ASC
        );
    }

    public static LocaleSearchRequest createLocaleSearchRequest(
        final String query,
        final Integer from,
        final Integer size,
        final LocaleSortField sortField,
        final SortOrder sortOrder
    ) {
        return new LocaleSearchRequest(query, from, size, sortField, sortOrder);
    }

    public static LocaleSortField getRandomLocaleSorField() {
        final Class<LocaleSortField> clazz = LocaleSortField.class;
        int x = new SecureRandom().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static LocaleModificationRequestDto createRandomLocaleModificationRequestDto() {
        return createLocaleModificationRequestDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static LocaleModificationRequestDto createLocaleModificationRequestDto(
        final String id,
        final String key,
        final String name
    ) {
        return new LocaleModificationRequestDto(id, key, name);
    }

    public static LocaleModificationRequest createRandomLocaleModificationRequest() {
        return createLocaleModificationRequest(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static LocaleModificationRequest createLocaleModificationRequest(
        final String id,
        final String key,
        final String name
    ) {
        return new LocaleModificationRequest(id, key, name);
    }
}
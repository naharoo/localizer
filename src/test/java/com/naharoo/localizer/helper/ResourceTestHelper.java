package com.naharoo.localizer.helper;

import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.*;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import com.naharoo.localizer.endpoint.resource.ResourceCreationRequestDto;
import com.naharoo.localizer.endpoint.resource.ResourceDto;
import com.naharoo.localizer.endpoint.resource.ResourceModificationRequestDto;
import com.naharoo.localizer.endpoint.resource.ResourceSearchRequestDto;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class ResourceTestHelper {

    private ResourceTestHelper() {
        throw new IllegalAccessError();
    }

    public static Resource createResource(
        final String id,
        final String key,
        final Locale locale,
        final String value,
        final LocalDateTime created,
        final LocalDateTime updated,
        final LocalDateTime deleted
    ) {
        final Resource resource = new Resource();
        resource.setId(id);
        resource.setKey(key);
        resource.setLocale(locale);
        resource.setValue(value);
        resource.setCreated(created);
        resource.setUpdated(updated);
        resource.setDeleted(deleted);
        return resource;
    }

    public static Resource createRandomResource() {
        return createResource(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocaleTestHelper.createRandomLocale(),
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    public static ResourceDto createResourceDto(
        final String id,
        final String key,
        final LocaleDto locale,
        final String value,
        final LocalDateTime created,
        final LocalDateTime updated,
        final LocalDateTime deleted
    ) {
        return new ResourceDto(id, key, locale, value, created, updated, deleted);
    }

    public static ResourceDto createRandomResourceDto() {
        return createResourceDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocaleTestHelper.createRandomLocaleDto(),
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    public static ResourceCreationRequest createResourceCreationRequest(
        final String key,
        final String localeId,
        final String value
    ) {
        return new ResourceCreationRequest(key, localeId, value);
    }

    public static ResourceCreationRequest createRandomResourceCreationRequest() {
        return createResourceCreationRequest(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static ResourceCreationRequestDto createResourceCreationRequestDto(
        final String key,
        final String localeId,
        final String value
    ) {
        return new ResourceCreationRequestDto(key, localeId, value);
    }

    public static ResourceCreationRequestDto createRandomResourceCreationRequestDto() {
        return createResourceCreationRequestDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static ResourceModificationRequest createResourceModificationRequest(
        final String id,
        final String key,
        final String value
    ) {
        return new ResourceModificationRequest(id, key, value);
    }

    public static ResourceModificationRequest createRandomResourceModificationRequest() {
        return createResourceModificationRequest(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static ResourceModificationRequestDto createResourceModificationRequestDto(
        final String id,
        final String key,
        final String value
    ) {
        return new ResourceModificationRequestDto(id, key, value);
    }

    public static ResourceModificationRequestDto createRandomResourceModificationRequestDto() {
        return createResourceModificationRequestDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
    }

    public static ResourceSearchRequest createResourceSearchRequest(
        final String query,
        final String localeQuery,
        final Integer from,
        final Integer size,
        final ResourceSortField sortField,
        final SortOrder sortOrder
    ) {
        return new ResourceSearchRequest(query, localeQuery, from, size, sortField, sortOrder);
    }

    public static ResourceSearchRequest createRandomResourceSearchRequest() {
        return createResourceSearchRequest(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            0,
            20,
            getRandomResourceSorField(),
            SortOrder.ASC
        );
    }

    public static ResourceSearchRequestDto createResourceSearchRequestDto(
        final String query,
        final String localeQuery,
        final Integer from,
        final Integer size,
        final ResourceSortField sortField,
        final SortOrder sortOrder
    ) {
        return new ResourceSearchRequestDto(query, localeQuery, from, size, sortField, sortOrder);
    }

    public static ResourceSearchRequestDto createRandomResourceSearchRequestDto() {
        return createResourceSearchRequestDto(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            ThreadLocalRandom.current().nextInt(),
            ThreadLocalRandom.current().nextInt(),
            getRandomResourceSorField(),
            SortOrder.ASC
        );
    }

    public static ResourceSortField getRandomResourceSorField() {
        final Class<ResourceSortField> clazz = ResourceSortField.class;
        int x = new SecureRandom().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}

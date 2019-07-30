package com.naharoo.localizer.service.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.resource.ResourceService;
import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@UnitTest
class ResourceManagerImplTest {

    @Mock
    ResourceService resourceService;

    @Mock
    LocaleService localeService;

    @InjectMocks
    ResourceManagerImpl manager;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            resourceService,
            localeService
        );
    }

    @ParameterizedTest(name = "Input {arguments}")
    @EmptyStringSource
    @DisplayName("CascadeDeleteLocale should throw IllegalArgumentException when input is not valid")
    void cascadeDeleteLocale_illegalArgs(final String localeId) {
        // Given
        // Illegal localeId

        // When
        assertThrows(IllegalArgumentException.class, () -> manager.cascadeDeleteLocale(localeId));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("CascadeDeleteLocale should call both locale delete and delete resource by locale")
    void cascadeDeleteLocale_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String localeId = locale.getId();
        final LocalDateTime deleted = locale.getDeleted();

        when(localeService.delete(localeId))
            .thenReturn(locale);
        doNothing()
            .when(resourceService).deleteByLocale(locale, deleted);

        // When
        final Locale actualLocale = manager.cascadeDeleteLocale(localeId);

        // Then
        assertThat(actualLocale)
            .isNotNull()
            .isSameAs(locale);
        verify(localeService).delete(localeId);
        verify(resourceService).deleteByLocale(locale, deleted);
    }

    @Test
    @DisplayName("CreateResource should throw IllegalArgumentException when input creationRequest is null")
    void createResource_illegalArgs() {
        // Given
        final ResourceCreationRequest creationRequest = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> manager.createResource(creationRequest));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("CreateResource should successfully get Locale and delegate it with key and value to ResourceService.")
    void createResource_normalCase() {
        // Given
        final ResourceCreationRequest creationRequest = ResourceTestHelper.createRandomResourceCreationRequest();

        final String key = creationRequest.getKey();
        final String localeId = creationRequest.getLocaleId();
        final String value = creationRequest.getValue();

        final Locale locale = LocaleTestHelper.createRandomLocale();
        final Resource resource = ResourceTestHelper.createRandomResource();

        when(localeService.getById(localeId))
            .thenReturn(locale);
        when(resourceService.create(key, locale, value))
            .thenReturn(resource);

        // When
        final Resource actualResource = manager.createResource(creationRequest);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isSameAs(resource);

        verify(localeService).getById(localeId);
        verify(resourceService).create(key, locale, value);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetResourceByKeyAndLocaleKey should throw IllegalArgumentException when provided Resource key is empty")
    void getResourceByKeyAndLocaleKey_illegalResourceKey(final String resourceKey) {
        // Given
        final String localeKey = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> manager.getResourceByKeyAndLocaleKey(resourceKey, localeKey));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetResourceByKeyAndLocaleKey should throw IllegalArgumentException when provided Locale key is empty")
    void getResourceByKeyAndLocaleKey_illegalLocaleKey(final String localeKey) {
        // Given
        final String resourceKey = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> manager.getResourceByKeyAndLocaleKey(resourceKey, localeKey));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetResourceByKeyAndLocaleKey should get Locale by localeKey and delegate resource key and Locale to resource service when input is valid")
    void getResourceByKeyAndLocaleKey_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String localeKey = locale.getKey();
        final Resource resource = ResourceTestHelper.createRandomResource();
        final String resourceKey = resource.getKey();

        when(localeService.getByKey(localeKey))
            .thenReturn(locale);
        when(resourceService.getByKeyAndLocale(resourceKey, locale))
            .thenReturn(resource);

        // When
        final Resource actualResource = manager.getResourceByKeyAndLocaleKey(resourceKey, localeKey);

        assertThat(actualResource)
            .isNotNull()
            .isSameAs(resource);
        verify(localeService).getByKey(localeKey);
        verify(resourceService).getByKeyAndLocale(resourceKey, locale);
    }
}
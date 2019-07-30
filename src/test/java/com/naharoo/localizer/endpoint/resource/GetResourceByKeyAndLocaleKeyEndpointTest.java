package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.ResourceManager;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetResourceByKeyAndLocaleKeyEndpointTest extends AbstractEndpointTest {

    @Mock
    ResourceManager manager;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(manager);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetResourceByKeyAndLocaleKey should throw IllegalArgumentException when provided key is empty")
    void getResourceByKeyAndLocaleKey_illegalKey(final String key) {
        // Given
        final String localeKey = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.getByKeyAndLocaleKey(key, localeKey));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetResourceByKeyAndLocaleKey should throw IllegalArgumentException when provided Locale key is empty")
    void getResourceByKeyAndLocaleKey_illegalLocaleKey(final String localeKey) {
        // Given
        final String key = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.getByKeyAndLocaleKey(key, localeKey));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetResourceByKeyAndLocaleKey should execute normally and escalate managers result when input is valid")
    void getResourceByKeyAndLocaleKey_normalCase() {
        // Given
        final Resource expectedResource = ResourceTestHelper.createRandomResource();
        final String key = expectedResource.getKey();
        final Locale locale = expectedResource.getLocale();
        final String localeKey = locale.getKey();

        when(manager.getResourceByKeyAndLocaleKey(key, localeKey))
            .thenReturn(expectedResource);

        // When
        final ResourceDto actualResource = endpoint.getByKeyAndLocaleKey(key, localeKey);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(expectedResource, "locale");
        assertThat(actualResource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(expectedResource.getLocale());
        verify(manager).getResourceByKeyAndLocaleKey(key, localeKey);
        verify(mapper).map(expectedResource, ResourceDto.class);
    }
}

package com.naharoo.localizer.service.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.helper.LocaleTestHelper;
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
}
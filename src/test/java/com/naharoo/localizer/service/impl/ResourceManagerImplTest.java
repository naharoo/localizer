package com.naharoo.localizer.service.impl;

import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.resource.ResourceService;
import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
}
package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetLocaleByIdEndpointTest extends AbstractEndpointTest {

    @Mock
    LocaleService localeService;

    @InjectMocks
    LocalesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(localeService);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetById should throw IllegalArgumentException when input is not valid")
    void getById_illegalArgs(final String id) {
        // Given
        // Blank id

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.getById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetById should execute normally and return proper locale when input is valid")
    void getById_normalExecution() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String id = locale.getId();

        when(localeService.getById(id))
            .thenReturn(locale);

        // When
        final LocaleDto result = endpoint.getById(id);

        // Then
        assertThat(result)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(locale);

        verify(localeService).getById(id);
        verify(mapper).map(locale, LocaleDto.class);
    }
}
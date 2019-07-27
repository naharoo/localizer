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

class GetLocaleByKeyEndpointTest extends AbstractEndpointTest {

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
    @DisplayName("GetByKey should throw IllegalArgumentException when input is not valid")
    void getByKey_illegalArgs(final String key) {
        // Given
        // Blank key

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.getByKey(key));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetByKey should execute normally and return proper locale when input is valid")
    void getByKey_normalExecution() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String key = locale.getId();

        when(localeService.getByKey(key))
            .thenReturn(locale);

        // When
        final LocaleDto result = endpoint.getByKey(key);

        // Then
        assertThat(result)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(locale);

        verify(localeService).getByKey(key);
        verify(mapper).map(locale, LocaleDto.class);
    }
}
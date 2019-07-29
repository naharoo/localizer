package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.service.locale.LocaleService;
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

class DeleteLocaleEndpointTest extends AbstractEndpointTest {

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
    @DisplayName("Delete should throw IllegalArgumentException when input is not valid")
    void delete_illegalArgs(final String id) {
        // Given
        // Blank id

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.delete(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Delete should execute normally and return proper locale when input is valid")
    void delete_normalExecution() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String id = locale.getId();

        when(localeService.delete(id))
            .thenReturn(locale);

        // When
        final LocaleDto result = endpoint.delete(id);

        // Then
        assertThat(result)
            .isNotNull()
            .isEqualToComparingFieldByField(locale);

        verify(localeService).delete(id);
        verify(mapper).map(locale, LocaleDto.class);
    }
}
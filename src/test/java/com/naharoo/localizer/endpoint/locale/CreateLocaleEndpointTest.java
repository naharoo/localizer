package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateLocaleEndpointTest extends AbstractEndpointTest {

    @Mock
    LocaleService localeService;

    @InjectMocks
    LocalesEndpointImpl endpoint;

    static Stream<LocaleCreationRequestDto> illegalLocaleCreationRequestDtos() {
        return Stream.of(
            null,
            new LocaleCreationRequestDto(null, null),
            new LocaleCreationRequestDto(null, UUID.randomUUID().toString()),
            new LocaleCreationRequestDto(UUID.randomUUID().toString(), null)
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(localeService);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocaleCreationRequestDtos")
    @DisplayName("Create Locales endpoint should throw IllegalArgumentException when Locale creation input is not valid")
    void create_illegalArgs(final LocaleCreationRequestDto request) {
        // Given
        // Illegal input


        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.create(request));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Create Locales endpoint should execute normally and return proper result when input is valid")
    void create_normalExecution() {
        // Given
        final String key = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();
        final LocaleCreationRequestDto requestDto = new LocaleCreationRequestDto(key, name);

        final Locale expectedLocale = LocaleTestHelper.createRandomLocale();

        doReturn(expectedLocale)
            .when(localeService).create(any(LocaleCreationRequest.class));

        // When
        final LocaleDto responseDto = endpoint.create(requestDto);

        // Then
        assertThat(responseDto)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(expectedLocale);

        verify(mapper).map(requestDto, LocaleCreationRequest.class);
        verify(localeService).create(any(LocaleCreationRequest.class));
        verify(mapper).map(expectedLocale, LocaleDto.class);
    }
}

package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleModificationRequest;
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

class UpdateLocaleEndpointTest extends AbstractEndpointTest {

    @Mock
    LocaleService localeService;

    @InjectMocks
    LocalesEndpointImpl endpoint;

    static Stream<LocaleModificationRequestDto> illegalLocaleModificationRequestDtos() {
        return Stream.of(
            null,
            new LocaleModificationRequestDto(null, null, null),
            new LocaleModificationRequestDto(null, UUID.randomUUID().toString(), UUID.randomUUID().toString()),
            new LocaleModificationRequestDto(UUID.randomUUID().toString(), null, UUID.randomUUID().toString()),
            new LocaleModificationRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null)
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(localeService);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocaleModificationRequestDtos")
    @DisplayName("Update Locales endpoint should throw IllegalArgumentException when Locale modification input is not valid")
    void update_illegalArgs(final LocaleModificationRequestDto request) {
        // Given
        // Illegal input


        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.update(request));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Update Locales endpoint should execute normally and return proper result when input is valid")
    void update_normalExecution() {
        // Given
        final LocaleModificationRequestDto requestDto = LocaleTestHelper.createRandomLocaleModificationRequestDto();
        final Locale expectedLocale = LocaleTestHelper.createRandomLocale();

        doReturn(expectedLocale)
            .when(localeService).update(any(LocaleModificationRequest.class));

        // When
        final LocaleDto responseDto = endpoint.update(requestDto);

        // Then
        assertThat(responseDto)
            .isNotNull()
            .isEqualToComparingFieldByField(expectedLocale);

        verify(mapper).map(requestDto, LocaleModificationRequest.class);
        verify(localeService).update(any(LocaleModificationRequest.class));
        verify(mapper).map(expectedLocale, LocaleDto.class);
    }
}

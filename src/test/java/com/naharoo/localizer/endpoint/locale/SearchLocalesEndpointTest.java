package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.service.locale.LocaleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchLocalesEndpointTest extends AbstractEndpointTest {

    @Mock
    LocaleService service;

    @InjectMocks
    LocalesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            service
        );
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullSource
    @DisplayName("GetLocales should throw IllegalArgumentException when input is invalid")
    void searchLocales_illegalArgs(final LocaleSearchRequestDto requestDto) {
        // Given
        // Illegal LocaleGetRequestDto

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.search(requestDto));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetLocales should return proper data when input is correct")
    void searchLocales_normalCase() {
        // Given
        final LocaleSearchRequestDto requestDto = LocaleTestHelper.createRandomLocaleSearchRequestDto();

        final List<Locale> expectedLocales = Arrays.asList(
            LocaleTestHelper.createRandomLocale(),
            LocaleTestHelper.createRandomLocale(),
            LocaleTestHelper.createRandomLocale()
        );
        final int expectedTotalItems = expectedLocales.size();

        when(service.search(any(LocaleSearchRequest.class)))
            .thenReturn(new GenericListResponse<>(expectedLocales, expectedTotalItems));

        // When
        final GenericListResponse<LocaleDto> response = endpoint.search(requestDto);

        // Then
        assertNotNull(response);
        final List<LocaleDto> actualLocales = response.getItems();
        assertThatListsAreEqualIgnoringFields(expectedLocales, actualLocales);
        final long actualTotalItems = response.getTotalItems();
        assertEquals(expectedTotalItems, actualTotalItems);

        verify(mapper).map(requestDto, LocaleSearchRequest.class);
        verify(service).search(any(LocaleSearchRequest.class));
        verify(mapper).mapAsList(expectedLocales, LocaleDto.class);
    }
}

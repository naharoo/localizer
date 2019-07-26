package com.naharoo.localizer.mapper.locale;

import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.endpoint.locale.LocaleSearchRequestDto;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleSearchRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("LocaleSearchRequest should be successfully mapped to LocaleSearchRequestDto")
    void localeSearchRequestToDto() {
        // Given
        final LocaleSearchRequest localeSearchRequest = LocaleTestHelper.createRandomLocaleSearchRequest();

        // When
        final LocaleSearchRequestDto localeSearchRequestDto = mapper.map(localeSearchRequest, LocaleSearchRequestDto.class);

        // Then
        assertThat(localeSearchRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(localeSearchRequest);
    }

    @Test
    @DisplayName("LocaleSearchRequestDto should be successfully mapped to LocaleSearchRequest")
    void localeSearchRequestDtoToDomain() {
        // Given
        final LocaleSearchRequestDto localeSearchRequestDto = LocaleTestHelper.createRandomLocaleSearchRequestDto();

        // When
        final LocaleSearchRequest localeSearchRequest = mapper.map(localeSearchRequestDto, LocaleSearchRequest.class);

        // Then
        assertThat(localeSearchRequest)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(localeSearchRequestDto);
    }
}

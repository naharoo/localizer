package com.naharoo.localizer.mapper.locale;

import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.endpoint.locale.LocaleCreationRequestDto;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleCreationRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("LocaleCreationRequest should be successfully mapped to LocaleCreationRequestDto")
    void localeCreationRequestToDto() {
        // Given
        final LocaleCreationRequest localeCreationRequest = LocaleTestHelper.createRandomLocaleCreationRequest();

        // When
        final LocaleCreationRequestDto localeCreationRequestDto = mapper.map(localeCreationRequest, LocaleCreationRequestDto.class);

        // Then
        assertThat(localeCreationRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(localeCreationRequest);
    }

    @Test
    @DisplayName("LocaleCreationRequestDto should be successfully mapped to LocaleCreationRequest")
    void localeCreationRequestDtoToDomain() {
        // Given
        final LocaleCreationRequestDto localeCreationRequestDto = LocaleTestHelper.createRandomLocaleCreationRequestDto();

        // When
        final LocaleCreationRequest localeCreationRequest = mapper.map(localeCreationRequestDto, LocaleCreationRequest.class);

        // Then
        assertThat(localeCreationRequest)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(localeCreationRequestDto);
    }
}

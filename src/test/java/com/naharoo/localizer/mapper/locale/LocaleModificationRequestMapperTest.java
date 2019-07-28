package com.naharoo.localizer.mapper.locale;

import com.naharoo.localizer.domain.locale.LocaleModificationRequest;
import com.naharoo.localizer.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleModificationRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("LocaleModificationRequest should be successfully mapped to LocaleModificationRequestDto")
    void localeModificationRequestToDto() {
        // Given
        final LocaleModificationRequest localeModificationRequest = LocaleTestHelper.createRandomLocaleModificationRequest();

        // When
        final LocaleModificationRequestDto localeModificationRequestDto = mapper.map(localeModificationRequest, LocaleModificationRequestDto.class);

        // Then
        assertThat(localeModificationRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByField(localeModificationRequest);
    }

    @Test
    @DisplayName("LocaleModificationRequestDto should be successfully mapped to LocaleModificationRequest")
    void localeModificationRequestDtoToDomain() {
        // Given
        final LocaleModificationRequestDto localeModificationRequestDto = LocaleTestHelper.createRandomLocaleModificationRequestDto();

        // When
        final LocaleModificationRequest localeModificationRequest = mapper.map(localeModificationRequestDto, LocaleModificationRequest.class);

        // Then
        assertThat(localeModificationRequest)
            .isNotNull()
            .isEqualToComparingFieldByField(localeModificationRequestDto);
    }
}

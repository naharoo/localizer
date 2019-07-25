package com.naharoo.localizer.mapper.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("Locale should be successfully mapped to LocaleDto")
    void localeToLocaleDto() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();

        // When
        final LocaleDto localeDto = mapper.map(locale, LocaleDto.class);

        // Then
        assertThat(localeDto)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(locale);
    }

    @Test
    @DisplayName("LocaleDto should be successfully mapped to Locale")
    void localeDtoToLocale() {
        // Given
        final LocaleDto localeDto = LocaleTestHelper.createRandomLocaleDto();

        // When
        final Locale locale = mapper.map(localeDto, Locale.class);

        // Then
        assertThat(locale)
            .isNotNull()
            .isEqualToComparingFieldByFieldRecursively(localeDto);
    }
}

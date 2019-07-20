package com.naharoo.localizer.api.facade.endpoint.locale;

import com.naharoo.localizer.api.facade.mapper.BeanMapper;
import com.naharoo.localizer.core.services.locale.Locale;
import com.naharoo.localizer.core.services.locale.LocaleModificationRequest;
import com.naharoo.localizer.core.services.locale.LocaleService;
import org.codehaus.commons.nullanalysis.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@RestController
@RequestMapping("/locales")
public class LocalesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(LocalesEndpoint.class);

    private final BeanMapper mapper;
    private final LocaleService localeService;

    public LocalesEndpoint(final BeanMapper mapper, final LocaleService localeService) {
        this.mapper = mapper;
        this.localeService = localeService;
    }

    @PostMapping
    public LocaleDto create(
        @Valid @NotNull @RequestBody final LocaleModificationRequestDto modificationRequestDto
    ) {
        expectNotNull(modificationRequestDto, "modificationRequestDto cannot be null.");
        final String key = modificationRequestDto.getKey();
        expectNotEmpty(key, "modificationRequestDto.key cannot be empty.");
        final String name = modificationRequestDto.getName();
        expectNotEmpty(name, "modificationRequestDto.name cannot be empty.");

        logger.debug("Creating Locale [key:'{}', name:'{}']...", key, name);

        final LocaleModificationRequest request = mapper.map(modificationRequestDto, LocaleModificationRequest.class);
        final Locale locale = localeService.create(request);
        final LocaleDto createdLocale = mapper.map(locale, LocaleDto.class);

        logger.info("Done creating Locale:'{}' [key:'{}', name:'{}'].", createdLocale.getId(), key, name);
        return createdLocale;
    }
}

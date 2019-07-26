package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.mapper.BeanMapper;
import com.naharoo.localizer.service.locale.LocaleService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@RestController
@Validated
public class LocalesEndpointImpl implements LocalesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(LocalesEndpointImpl.class);

    private final BeanMapper mapper;
    private final LocaleService localeService;

    public LocalesEndpointImpl(final BeanMapper mapper, final LocaleService localeService) {
        this.mapper = mapper;
        this.localeService = localeService;
    }

    @Override
    public LocaleDto create(final LocaleCreationRequestDto modificationRequestDto) {
        expectNotNull(modificationRequestDto, "modificationRequestDto cannot be null.");
        final String key = modificationRequestDto.getKey();
        expectNotEmpty(key, "modificationRequestDto.key cannot be empty.");
        final String name = modificationRequestDto.getName();
        expectNotEmpty(name, "modificationRequestDto.name cannot be empty.");

        logger.debug("Creating Locale [key:'{}', name:'{}']...", key, name);

        final LocaleCreationRequest request = mapper.map(modificationRequestDto, LocaleCreationRequest.class);
        final Locale locale = localeService.create(request);
        final LocaleDto createdLocale = mapper.map(locale, LocaleDto.class);

        logger.info("Done creating Locale:'{}' [key:'{}', name:'{}'].", createdLocale.getId(), key, name);
        return createdLocale;
    }

    @Override
    public LocaleDto getById(final String id) {
        expectNotEmpty(id, "id cannot be empty.");

        logger.debug("Getting Locale by id:'{}'...", id);

        final Locale locale = localeService.getById(id);
        final LocaleDto result = mapper.map(locale, LocaleDto.class);

        logger.info("Getting Locale by id:'{}'.", id);
        return result;
    }

    @Override
    public GenericListResponse<LocaleDto> search(final LocaleSearchRequestDto requestDto) {
        expectNotNull(requestDto, "requestDto cannot be null.");

        logger.debug("Searching for Locales...");
        final StopWatch stopwatch = StopWatch.createStarted();

        final LocaleSearchRequest request = mapper.map(requestDto, LocaleSearchRequest.class);
        final GenericListResponse<Locale> locales = localeService.search(request);
        final GenericListResponse<LocaleDto> response = new GenericListResponse<>(
            mapper.mapAsList(locales.getItems(), LocaleDto.class),
            locales.getTotalItems()
        );

        stopwatch.stop();
        logger.debug(
            "Done searching for {} of total {} Locales in {}ms.",
            response.getItems().size(),
            response.getTotalItems(),
            stopwatch.getTime()
        );
        return response;
    }
}
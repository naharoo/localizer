package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleModificationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.mapper.BeanMapper;
import com.naharoo.localizer.service.ResourceManager;
import com.naharoo.localizer.service.locale.LocaleService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@Controller
public class LocalesEndpointImpl implements LocalesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(LocalesEndpointImpl.class);

    private final BeanMapper mapper;
    private final LocaleService localeService;
    private final ResourceManager resourceManager;

    public LocalesEndpointImpl(
        final BeanMapper mapper,
        final LocaleService localeService,
        final ResourceManager resourceManager
    ) {
        this.mapper = mapper;
        this.localeService = localeService;
        this.resourceManager = resourceManager;
    }

    @Override
    public LocaleDto create(final LocaleCreationRequestDto creationRequestDto) {
        expectNotNull(creationRequestDto, "creationRequestDto cannot be null.");
        final String key = creationRequestDto.getKey();
        expectNotEmpty(key, "creationRequestDto.key cannot be empty.");
        final String name = creationRequestDto.getName();
        expectNotEmpty(name, "creationRequestDto.name cannot be empty.");

        logger.debug("Creating Locale [key:'{}', name:'{}']...", key, name);

        final LocaleCreationRequest request = mapper.map(creationRequestDto, LocaleCreationRequest.class);
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

        logger.info("Done getting Locale by id:'{}'.", id);
        return result;
    }

    @Override
    public LocaleDto getByKey(final String key) {
        expectNotEmpty(key, "key cannot be empty.");

        logger.debug("Getting Locale by key:'{}'...", key);

        final Locale locale = localeService.getByKey(key);
        final LocaleDto result = mapper.map(locale, LocaleDto.class);

        logger.info("Done getting Locale by key:'{}'.", key);
        return result;
    }

    @Override
    public GenericListResponse<LocaleDto> search(final LocaleSearchRequestDto searchRequestDto) {
        expectNotNull(searchRequestDto, "searchRequestDto cannot be null.");

        logger.debug("Searching for Locales...");
        final StopWatch stopwatch = StopWatch.createStarted();

        final LocaleSearchRequest request = mapper.map(searchRequestDto, LocaleSearchRequest.class);
        final GenericListResponse<Locale> locales = localeService.search(request);
        final GenericListResponse<LocaleDto> response = new GenericListResponse<>(
            mapper.mapAsList(locales.getItems(), LocaleDto.class),
            locales.getTotalItems()
        );

        stopwatch.stop();
        logger.info(
            "Done searching for {} of total {} Locales in {}ms.",
            response.getItems().size(),
            response.getTotalItems(),
            stopwatch.getTime()
        );
        return response;
    }

    @Override
    public LocaleDto delete(final String id) {
        expectNotEmpty(id, "id cannot be empty.");

        logger.debug("Deleting Locale by id:'{}'...", id);

        final Locale locale = resourceManager.cascadeDeleteLocale(id);
        final LocaleDto result = mapper.map(locale, LocaleDto.class);

        logger.info("Done deleting Locale by id:'{}'.", id);
        return result;
    }

    @Override
    public LocaleDto update(final LocaleModificationRequestDto modificationRequestDto) {
        expectNotNull(modificationRequestDto, "modificationRequestDto cannot be null.");
        final String id = modificationRequestDto.getId();
        expectNotEmpty(id, "modificationRequestDto.id cannot be empty.");
        expectNotEmpty(modificationRequestDto.getKey(), "modificationRequestDto.key cannot be empty.");
        expectNotEmpty(modificationRequestDto.getName(), "modificationRequestDto.name cannot be empty.");

        logger.debug("Updating Locale:'{}' with ['{}'] details...", id, modificationRequestDto);

        final LocaleModificationRequest modificationRequest = mapper.map(modificationRequestDto, LocaleModificationRequest.class);
        final Locale updatedLocale = localeService.update(modificationRequest);
        final LocaleDto result = mapper.map(updatedLocale, LocaleDto.class);

        logger.info("Done updating Locale:'{}' with ['{}'] details.", id, modificationRequestDto);
        return result;
    }
}
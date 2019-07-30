package com.naharoo.localizer.service.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.service.ResourceManager;
import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@Component
public class ResourceManagerImpl implements ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManagerImpl.class);

    private final ResourceService resourceService;
    private final LocaleService localeService;

    public ResourceManagerImpl(final ResourceService resourceService, final LocaleService localeService) {
        this.resourceService = resourceService;
        this.localeService = localeService;
    }

    @Transactional
    @Override
    public Locale cascadeDeleteLocale(final String localeId) {
        expectNotEmpty(localeId, "localeId cannot be empty.");
        logger.trace("Deleting Locale:'{}' and all resources associated with it...", localeId);

        final Locale deletedLocale = localeService.delete(localeId);
        final LocalDateTime deleted = deletedLocale.getDeleted();
        resourceService.deleteByLocale(deletedLocale, deleted);

        logger.debug("Done deleting Locale:'{}' and all Resources associated with it.", localeId);
        return deletedLocale;
    }

    @Transactional
    @Override
    public Resource createResource(final ResourceCreationRequest creationRequest) {
        expectNotNull(creationRequest, "creationRequest cannot be null.");

        final String key = creationRequest.getKey();
        final String localeId = creationRequest.getLocaleId();
        final String value = creationRequest.getValue();

        logger.trace(
            "Creating resource with [key: '{}', localeId: '{}', value: '{}']...",
            key,
            localeId,
            value
        );

        final Locale locale = localeService.getById(localeId);
        final Resource createdResource = resourceService.create(key, locale, value);

        logger.debug(
            "Done creating Resource:'{}' with [key: '{}', localeId: '{}', value: '{}']...",
            createdResource.getId(),
            key,
            localeId,
            value
        );
        return createdResource;
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getResourceByKeyAndLocaleKey(final String resourceKey, final String localeKey) {
        expectNotEmpty(resourceKey, "resourceKey cannot be empty.");
        expectNotEmpty(localeKey, "localeKey cannot be empty.");

        logger.trace("Getting Resource with [resourceKey: '{}', localeKey: '{}']...", resourceKey, localeKey);

        final Locale locale = localeService.getByKey(localeKey);
        final Resource resource = resourceService.getByKeyAndLocale(resourceKey, locale);

        logger.debug(
            "Done getting Resource:'{}' with [resourceKey: '{}', localeKey: '{}']...",
            resource.getId(),
            resourceKey,
            localeKey
        );
        return resource;
    }
}

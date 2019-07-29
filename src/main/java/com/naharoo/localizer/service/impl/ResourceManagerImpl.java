package com.naharoo.localizer.service.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.service.ResourceManager;
import com.naharoo.localizer.service.locale.LocaleService;
import com.naharoo.localizer.service.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;

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
}

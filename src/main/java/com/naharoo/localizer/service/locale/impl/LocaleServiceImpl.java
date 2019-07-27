package com.naharoo.localizer.service.locale.impl;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.repository.LocaleRepository;
import com.naharoo.localizer.service.locale.LocaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;
import static com.naharoo.localizer.utils.PaginationUtils.toPageRequest;

@Service
public class LocaleServiceImpl implements LocaleService {

    private static final Logger logger = LoggerFactory.getLogger(LocaleServiceImpl.class);

    private final LocaleRepository localeRepository;

    public LocaleServiceImpl(final LocaleRepository localeRepository) {
        this.localeRepository = localeRepository;
    }

    @Transactional
    @Override
    public Locale create(final LocaleCreationRequest creationRequest) {
        expectNotNull(creationRequest, "creationRequest cannot be null.");
        final String key = creationRequest.getKey();
        expectNotEmpty(key, "creationRequest.key cannot be empty.");
        final String name = creationRequest.getName();
        expectNotEmpty(name, "creationRequest.name cannot be empty.");

        logger.trace("Creating Locale [key:'{}', name:'{}']...", key, name);

        findByKey(key).ifPresent(locale -> {
            logger.warn("Another Locale with key '{}' already exists.", key);
            throw ResourceAlreadyExistsException.with(
                Locale.class,
                "key",
                key
            );
        });

        final Locale newLocale = new Locale(key, name);

        final Locale createdLocale = localeRepository.save(newLocale);

        logger.debug("Done creating Locale:'{}' [key:'{}', name:'{}']...", createdLocale.getId(), key, name);
        return createdLocale;
    }

    @Transactional(readOnly = true)
    @Override
    public Locale getByKey(final String key) {
        expectNotEmpty(key, "key cannot be empty.");
        logger.trace("Getting Locale by key:'{}'...", key);

        final Optional<Locale> localeOpt = findByKey(key);
        if (localeOpt.isEmpty()) {
            logger.warn("No Locale has been found with key '{}'.", key);
            throw ResourceNotFoundException.with(
                Locale.class,
                "key",
                key
            );
        }
        final Locale result = localeOpt.get();

        logger.debug("Done getting Locale by key:'{}'.", key);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Locale> findByKey(final String key) {
        expectNotEmpty(key, "key cannot be empty.");

        logger.trace("Finding Locale by key '{}'...", key);

        final Optional<Locale> localeOpt = localeRepository.findByKeyIgnoreCaseAndDeletedIsNull(key);

        logger.debug("Done finding Locale by key '{}'...", key);
        return localeOpt;
    }

    @Transactional(readOnly = true)
    @Override
    public Locale getById(final String id) {
        expectNotEmpty(id, "id cannot be empty.");
        logger.trace("Getting Locale by id:'{}'...", id);

        final Optional<Locale> localeOpt = findById(id);
        if (localeOpt.isEmpty()) {
            logger.warn("No Locale has been found with id '{}'.", id);
            throw ResourceNotFoundException.with(
                Locale.class,
                "id",
                id
            );
        }
        final Locale result = localeOpt.get();

        logger.debug("Done getting Locale by id:'{}'.", id);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Locale> findById(final String id) {
        expectNotEmpty(id, "id cannot be empty.");

        logger.trace("Finding Locale by id '{}'...", id);

        final Optional<Locale> localeOpt = localeRepository.findByIdAndDeletedIsNull(id);

        logger.debug("Done finding Locale by id '{}'...", id);
        return localeOpt;
    }

    @Transactional(readOnly = true)
    @Override
    public GenericListResponse<Locale> search(final LocaleSearchRequest searchRequest) {
        expectNotNull(searchRequest, "searchRequest cannot be null.");

        logger.trace("Searching for Locales...");

        final Page<Locale> page = localeRepository.search(
            toPageRequest(
                searchRequest.getFrom(),
                searchRequest.getSize(),
                searchRequest.getSortField(),
                searchRequest.getSortOrder()
            )
        );

        final GenericListResponse<Locale> result = new GenericListResponse<>(
            page.getContent(),
            page.getTotalElements()
        );

        logger.debug(
            "Done searching for {} of total {} Locales.",
            result.getItems().size(),
            result.getTotalItems()
        );
        return result;
    }

}
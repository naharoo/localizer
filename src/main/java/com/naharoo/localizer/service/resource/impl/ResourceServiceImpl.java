package com.naharoo.localizer.service.resource.impl;

import com.google.common.collect.ImmutableMap;
import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.SortOrder;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.domain.resource.ResourceSearchRequest;
import com.naharoo.localizer.domain.resource.ResourceSortField;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.repository.ResourceRepository;
import com.naharoo.localizer.service.resource.ResourceService;
import com.naharoo.localizer.utils.PaginationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Transactional
    @Override
    public Resource delete(final String id) {
        expectNotEmpty(id, "id cannot be empty.");
        logger.trace("Deleting Resource:'{}'...", id);

        final Resource resource = getById(id);
        final LocalDateTime currentDateTime = LocalDateTime.now();
        resource.setDeleted(currentDateTime);
        final Resource modifiedResource = resourceRepository.save(resource);

        logger.trace("Done deleting Resource:'{}'.", id);
        return modifiedResource;
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getById(final String id) {
        expectNotEmpty(id, "id cannot be empty.");
        logger.trace("Getting Resource:'{}'...", id);

        final Optional<Resource> resourceOpt = findById(id);
        if (resourceOpt.isEmpty()) {
            logger.warn("No Resource has been found with id '{}'.", id);
            throw ResourceNotFoundException.with(
                Resource.class,
                "id",
                id
            );
        }

        logger.debug("Done getting Resource:'{}'.", id);
        return resourceOpt.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Resource> findById(final String id) {
        expectNotEmpty(id, "id cannot be empty.");
        logger.trace("Finding Resource:'{}'...", id);

        final Optional<Resource> resourceOpt = resourceRepository.findByIdAndDeletedIsNull(id);
        if (resourceOpt.isPresent()) {
            logger.debug("Done finding Resource:'{}'.", id);
        } else {
            logger.debug("No Resource has been found with id: '{}'.", id);
        }

        return resourceOpt;
    }

    @Transactional
    @Override
    public Resource update(final ResourceModificationRequest modificationRequest) {
        expectNotNull(modificationRequest, "modificationRequest cannot be null.");
        final String id = modificationRequest.getId();
        final String key = modificationRequest.getKey();
        final String value = modificationRequest.getValue();

        logger.trace(
            "Updating Resource:'{}' with properties: [key: '{}', value: '{}']...",
            id,
            key,
            value
        );

        final Resource resource = getById(id);
        resource.setKey(key);
        resource.setValue(value);
        final Resource modifiedResource = resourceRepository.save(resource);

        logger.debug(
            "Done updating Resource:'{}' with properties: [key: '{}', value: '{}'].",
            id,
            key,
            value
        );
        return modifiedResource;
    }

    @Transactional
    @Override
    public void deleteByLocale(final Locale locale, final LocalDateTime deleted) {
        expectNotNull(deleted, "deleted cannot be null.");
        expectNotNull(locale, "locale cannot be null.");
        final String localeId = locale.getId();
        expectNotEmpty(localeId, "locale.id cannot be empty.");
        final LocalDateTime localeDeleted = locale.getDeleted();
        expectNotNull(localeDeleted, "locale.deleted cannot be null.");

        logger.trace("Deleting all batches with Locale:'{}'... Setting deleted to '{}'.", localeId, deleted);

        resourceRepository.deleteByLocaleId(localeId, deleted);

        logger.debug(
            "Done deleting all batches with Locale:'{}'. Their deleted field has been set to '{}'.",
            localeId,
            deleted
        );
    }

    @Transactional
    @Override
    public Resource create(final String key, final Locale locale, final String value) {
        expectNotEmpty(key, "key cannot be empty.");
        expectNotEmpty(value, "value cannot be empty.");
        expectNotNull(locale, "locale cannot be empty.");
        final String localeId = locale.getId();
        expectNotEmpty(localeId, "locale.id cannot be empty.");

        logger.trace("Creating Resource with key '{}' and value '{}' in Locale:'{}'...", key, value, localeId);

        final Optional<Resource> resourceOpt = findByKeyAndLocale(key, locale);
        if (resourceOpt.isPresent()) {
            logger.warn("Another Resource with key:'{}' and Locale:'{}' already exists.", key, localeId);
            throw ResourceAlreadyExistsException.with(
                Resource.class,
                ImmutableMap.<String, Object>builder()
                    .put("key", key)
                    .put("localeId", localeId)
                    .build()
            );
        }

        final Resource resource = new Resource(key, locale, value);
        final Resource createdResource = resourceRepository.save(resource);

        logger.debug(
            "Done creating Resource:'{}' with key '{}' and value '{}' in Locale:'{}'...",
            createdResource.getId(),
            key,
            value,
            localeId
        );
        return createdResource;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Resource> findByKeyAndLocale(final String key, final Locale locale) {
        expectNotEmpty(key, "key cannot be empty.");
        expectNotNull(locale, "locale cannot be null.");
        final String localeId = locale.getId();
        expectNotEmpty(localeId, "locale.id cannot be empty.");

        logger.trace("Finding Resource with key:'{}' and Locale:'{}'...", key, localeId);

        final Optional<Resource> resourceOpt = resourceRepository.findByKeyAndLocaleId(key, localeId);

        logger.debug("Done finding Resource with key:'{}' and Locale:'{}'.", key, localeId);
        return resourceOpt;
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getByKeyAndLocale(final String key, final Locale locale) {
        expectNotEmpty(key, "key cannot be empty.");
        expectNotNull(locale, "locale cannot be null.");
        final String localeId = locale.getId();
        expectNotEmpty(localeId, "locale.id cannot be empty.");

        logger.trace("Getting Resource with [key: '{}', locale: '{}']", key, locale);

        final Optional<Resource> resourceOpt = findByKeyAndLocale(key, locale);
        if (resourceOpt.isEmpty()) {
            logger.warn("No Resource has been found with key '{}' and localeId '{}'.", key, localeId);
            throw ResourceNotFoundException.with(
                Resource.class,
                ImmutableMap.<String, Object>builder()
                    .put("key", key)
                    .put("localeId", localeId)
                    .build()
            );
        }
        final Resource resource = resourceOpt.get();

        logger.debug("Done getting Resource:'{}' with [key: '{}', locale: '{}']", resource.getId(), key, locale);
        return resource;
    }

    @Transactional(readOnly = true)
    @Override
    public GenericListResponse<Resource> search(final ResourceSearchRequest searchRequest) {
        expectNotNull(searchRequest, "searchRequest cannot be null.");

        final String query = searchRequest.getQuery();
        final String localeQuery = searchRequest.getLocaleQuery();
        final int from = searchRequest.getFrom();
        final int size = searchRequest.getSize();
        final ResourceSortField sortField = searchRequest.getSortField();
        final SortOrder sortOrder = searchRequest.getSortOrder();

        logger.trace(
            "Searching for Resources with [query: '{}', localeQuery: '{}', from: {}, size: {}, sortField: {}, " +
                "sortOrder: {}]",
            query,
            localeQuery,
            from,
            size,
            sortField,
            sortOrder
        );

        final PageRequest pageRequest = PaginationUtils.toPageRequest(from, size, sortField, sortOrder);
        final Page<Resource> resourcePage = resourceRepository.search(query, localeQuery, pageRequest);
        final List<Resource> resources = resourcePage.getContent();
        final long totalItems = resourcePage.getTotalElements();
        final GenericListResponse<Resource> response = new GenericListResponse<>(resources, totalItems);

        logger.debug(
            "Done searching for {} of total {} Resources with [query: '{}', localeQuery: '{}', from: {}, " +
                "size: {}, sortField: {}, sortOrder: {}]",
            resourcePage.getSize(),
            totalItems,
            query,
            localeQuery,
            from,
            size,
            sortField,
            sortOrder
        );
        return response;
    }

}

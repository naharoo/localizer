package com.naharoo.localizer.service.resource.impl;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.repository.ResourceRepository;
import com.naharoo.localizer.service.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

}

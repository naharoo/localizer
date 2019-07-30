package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.mapper.BeanMapper;
import com.naharoo.localizer.service.ResourceManager;
import com.naharoo.localizer.service.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

@Component
public class ResourcesEndpointImpl implements ResourcesEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesEndpointImpl.class);

    private final BeanMapper mapper;
    private final ResourceService resourceService;
    private final ResourceManager manager;

    public ResourcesEndpointImpl(
        final BeanMapper mapper,
        final ResourceService resourceService,
        final ResourceManager manager
    ) {
        this.mapper = mapper;
        this.resourceService = resourceService;
        this.manager = manager;
    }

    @Override
    public ResourceDto create(final ResourceCreationRequestDto creationRequestDto) {
        expectNotNull(creationRequestDto, "creationRequestDto cannot be null.");
        final String key = creationRequestDto.getKey();
        expectNotEmpty(key, "creationRequestDto.key cannot be empty.");
        final String value = creationRequestDto.getValue();
        expectNotEmpty(value, "creationRequestDto.value cannot be empty.");
        final String localeId = creationRequestDto.getLocaleId();
        expectNotEmpty(localeId, "creationRequestDto.localeId cannot be empty.");

        logger.debug(
            "Creating Resource with [key: '{}', localeId: '{}', value: '{}']...",
            key,
            localeId,
            value
        );

        final ResourceCreationRequest creationRequest = mapper.map(creationRequestDto, ResourceCreationRequest.class);
        final Resource createdResource = manager.createResource(creationRequest);
        final ResourceDto result = mapper.map(createdResource, ResourceDto.class);

        logger.info(
            "Done creating Resource:'{}' with [key: '{}', localeId: '{}', value: '{}']...",
            result.getId(),
            key,
            localeId,
            value
        );
        return result;
    }
}

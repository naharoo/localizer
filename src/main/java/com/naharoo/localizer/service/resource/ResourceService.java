package com.naharoo.localizer.service.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;

import java.util.Optional;

public interface ResourceService {

    Resource delete(String id);

    Resource getById(String id);

    Optional<Resource> findById(String id);

    Resource update(ResourceModificationRequest modificationRequest);
}

package com.naharoo.localizer.service.resource;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.domain.resource.ResourceSearchRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ResourceService {

    Resource delete(String id);

    Resource getById(String id);

    Optional<Resource> findById(String id);

    Resource update(ResourceModificationRequest modificationRequest);

    void deleteByLocale(Locale locale, LocalDateTime deleted);

    Resource create(String key, Locale locale, String value);

    Optional<Resource> findByKeyAndLocale(String key, Locale locale);

    Resource getByKeyAndLocale(String key, Locale locale);

    GenericListResponse<Resource> search(ResourceSearchRequest searchRequest);
}

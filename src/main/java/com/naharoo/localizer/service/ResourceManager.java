package com.naharoo.localizer.service;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;

public interface ResourceManager {

    Locale cascadeDeleteLocale(String localeId);

    Resource createResource(ResourceCreationRequest creationRequest);

    Resource getResourceByKeyAndLocaleKey(String resourceKey, String localeKey);
}

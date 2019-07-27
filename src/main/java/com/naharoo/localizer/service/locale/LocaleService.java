package com.naharoo.localizer.service.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;

import java.util.Optional;

public interface LocaleService {

    Locale create(LocaleCreationRequest creationRequest);

    Locale getByKey(String key);

    Optional<Locale> findByKey(String key);

    Locale getById(String id);

    Optional<Locale> findById(String id);

    GenericListResponse<Locale> search(LocaleSearchRequest searchRequest);
}
package com.naharoo.localizer.service.locale;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;

import java.util.Optional;

public interface LocaleService {

    Locale create(LocaleCreationRequest creationRequest);

    Optional<Locale> findByKey(String key);
}
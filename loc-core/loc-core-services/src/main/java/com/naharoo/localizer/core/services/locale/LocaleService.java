package com.naharoo.localizer.core.services.locale;

import java.util.Optional;

public interface LocaleService {

    Locale create(LocaleModificationRequest creationRequest);

    Optional<Locale> findByKey(String key);
}

package com.naharoo.localizer.service;

import com.naharoo.localizer.domain.locale.Locale;

public interface ResourceManager {

    Locale cascadeDeleteLocale(String localeId);
}

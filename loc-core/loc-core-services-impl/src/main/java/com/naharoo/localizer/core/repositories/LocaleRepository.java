package com.naharoo.localizer.core.repositories;

import com.naharoo.localizer.core.services.locale.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocaleRepository extends JpaRepository<Locale, String> {

    Optional<Locale> findByKeyIgnoreCase(String key);
}

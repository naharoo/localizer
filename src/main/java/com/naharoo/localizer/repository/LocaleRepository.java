package com.naharoo.localizer.repository;

import com.naharoo.localizer.domain.locale.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocaleRepository extends JpaRepository<Locale, String> {

    Optional<Locale> findByKeyIgnoreCase(String key);
}
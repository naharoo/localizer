package com.naharoo.localizer.repository;

import com.naharoo.localizer.domain.locale.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface LocaleRepository extends JpaRepository<Locale, String> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Locale> findByKeyIgnoreCaseAndDeletedIsNull(String key);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Locale> findByIdAndDeletedIsNull(String id);

    @Query("select l from Locale l where l.deleted is null")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<Locale> search(Pageable pageable);
}
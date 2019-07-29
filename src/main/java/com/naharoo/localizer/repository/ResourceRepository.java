package com.naharoo.localizer.repository;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, String> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Resource> findByIdAndDeletedIsNull(String id);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Optional<Resource> findByKeyIgnoreCaseAndLocaleAndDeletedIsNull(String key, Locale locale);

    @Query("select r " +
        "   from Resource r " +
        "   where (r.deleted is null) " +
        "     and (:localeQuery is null " +
        "            or lower(r.locale.key) like lower(concat('%', '', :localeQuery, '%')) " +
        "            or lower(r.locale.name) like lower(concat('%', '', :localeQuery, '%'))) " +
        "     and (:query is null " +
        "            or lower(r.key) like lower(concat('%', '', :query, '%'))) ")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    Page<Resource> search(@Param("query") String query, @Param("localeQuery") String localeQuery, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Resource r " +
        "   set r.deleted = :deleted " +
        "   where r.locale.id = :localeId ")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    void deleteByLocaleId(@Param("localeId") String localeId, @Param("deleted") LocalDateTime deleted);
}

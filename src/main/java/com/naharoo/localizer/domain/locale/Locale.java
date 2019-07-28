package com.naharoo.localizer.domain.locale;

import com.naharoo.localizer.domain.Domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "l_locale",
    uniqueConstraints = {
        @UniqueConstraint(name = "l_locale_id_deleted_uk", columnNames = {"id", "deleted"}),
        @UniqueConstraint(name = "l_locale_key_deleted_uk", columnNames = {"key", "deleted"})
    },
    indexes = {
        @Index(name = "l_locale_id_deleted_index", columnList = "id, deleted", unique = true),
        @Index(name = "l_locale_key_deleted_index", columnList = "key, deleted", unique = true)
    }
)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Locale extends Domain {

    private static final long serialVersionUID = 8379431887787799490L;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    public Locale() {
        super();
    }

    public Locale(final String key, final String name) {
        this.key = key;
        this.name = name;
    }
}
package com.naharoo.localizer.domain.resource;

import com.naharoo.localizer.domain.Domain;
import com.naharoo.localizer.domain.locale.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "l_resource",
    uniqueConstraints = {
        @UniqueConstraint(name = "l_resource_key_locale_deleted_uk", columnNames = {"key", "locale_id", "deleted"})
    },
    indexes = {
        @Index(name = "l_resource_key_locale_deleted_uk", columnList = "key, locale_id, deleted", unique = true),
        @Index(name = "l_resource_key_deleted_index", columnList = "key, deleted")
    }
)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends Domain {

    private static final long serialVersionUID = 3170459537720536297L;

    @Column(name = "key", nullable = false)
    private String key;

    @ManyToOne
    @JoinColumn(
        name = "locale_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "l_resource_locale_id_locale_fk"),
        nullable = false,
        updatable = false
    )
    private Locale locale;

    @Column(name = "value", nullable = false)
    private String value;

    public Resource() {
        this(null, null, null);
    }

    public Resource(final String key, final Locale locale, final String value) {
        super(null, null, null, null);
        this.key = key;
        this.locale = locale;
        this.value = value;
    }
}

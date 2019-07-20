package com.naharoo.localizer.core.services.locale;

import com.naharoo.localizer.core.services.Domain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "l_locale",
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Locale locale = (Locale) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(key, locale.key)
            .append(name, locale.name)
            .append(deleted, locale.deleted)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(key)
            .append(name)
            .append(deleted)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("key", key)
            .append("name", name)
            .append("deleted", deleted)
            .toString();
    }
}

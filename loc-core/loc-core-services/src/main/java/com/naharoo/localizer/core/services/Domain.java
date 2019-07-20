package com.naharoo.localizer.core.services;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(UuidGeneratorListener.class)
public abstract class Domain implements Serializable {

    private static final long serialVersionUID = -4063990489634592490L;

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }

    public Domain() {
        super();
    }

    public Domain(final String id, final LocalDateTime created, final LocalDateTime updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        return new EqualsBuilder()
            .append(id, domain.id)
            .append(created, domain.created)
            .append(updated, domain.updated)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(created)
            .append(updated)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("created", created)
            .append("updated", updated)
            .toString();
    }
}
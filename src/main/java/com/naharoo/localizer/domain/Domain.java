package com.naharoo.localizer.domain;

import com.naharoo.localizer.domain.listener.UuidGeneratorListener;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
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

    public Domain(final String id, final LocalDateTime created, final LocalDateTime updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }
}
package com.naharoo.localizer.core.services;

import javax.persistence.PrePersist;
import java.util.UUID;

public class UuidGeneratorListener {

    @PrePersist
    public void setId(final Domain domain) {
        if (domain.getId() == null) {
            domain.setId(UUID.randomUUID().toString());
        }
    }
}

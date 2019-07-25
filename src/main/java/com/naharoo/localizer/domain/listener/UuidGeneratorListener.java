package com.naharoo.localizer.domain.listener;

import com.naharoo.localizer.domain.Domain;

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
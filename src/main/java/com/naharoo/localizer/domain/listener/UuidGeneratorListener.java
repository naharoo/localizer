package com.naharoo.localizer.domain.listener;

import com.naharoo.localizer.domain.Domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UuidGeneratorListener {

    @PrePersist
    public void setId(final Domain domain) {
        if (domain.getId() == null) {
            domain.setId(UUID.randomUUID().toString());
        }

        final LocalDateTime currentDateTime = LocalDateTime.now();
        domain.setCreated(currentDateTime);
        domain.setUpdated(currentDateTime);
    }

    @PreUpdate
    protected void onUpdate(final Domain domain) {
        domain.setUpdated(LocalDateTime.now());
    }
}
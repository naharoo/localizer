package com.naharoo.localizer.domain.locale;

import com.naharoo.localizer.domain.SortField;

public enum LocaleSortField implements SortField {
    ID("id"),
    KEY("key"),
    NAME("name"),
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String fieldName;

    LocaleSortField(final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

}

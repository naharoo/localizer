package com.naharoo.localizer.domain.resource;

import com.naharoo.localizer.domain.SortField;

public enum ResourceSortField implements SortField {
    ID("id"),
    KEY("key"),
    LOCALE_ID("locale.id"),
    LOCALE_KEY("locale.key"),
    LOCALE_NAME("locale.name"),
    VALUE("value"),
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String fieldName;

    ResourceSortField(final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}

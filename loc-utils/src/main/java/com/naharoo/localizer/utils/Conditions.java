package com.naharoo.localizer.utils;

import java.util.Collection;

public final class Conditions {

    private Conditions() {
    }

    public static boolean isBlank(final String string) {
        return string == null || string.length() == 0 || string.trim().length() == 0;
    }

    public static boolean isNotBlank(final String string) {
        return !isBlank(string);
    }

    public static boolean isNullOrEmptyArray(final Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotNullOrEmptyArray(final Object[] array) {
        return !isNullOrEmptyArray(array);
    }

    public static boolean isNullOrEmptyCollection(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotNullOrEmptyCollection(final Collection collection) {
        return !isNullOrEmptyCollection(collection);
    }

    public static boolean isNull(final Object object) {
        return object == null;
    }

    public static boolean isNotNull(final Object object) {
        return !isNull(object);
    }
}

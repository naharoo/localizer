package com.naharoo.localizer.utils;

import static com.naharoo.localizer.utils.Conditions.isNotBlank;

public final class Assertions {

    private Assertions() {
    }

    public static void expectNotNull(final Object object, final String message, final String... args) {
        expect(object != null, message, args);
    }

    public static void expectNotNullOrEmptyArray(final Object[] objArray, final String message, final String... args) {
        expectNotNull(objArray, "array is null.");
        expect(objArray.length != 0, message, args);
    }

    public static void expectNotEmpty(final String string, final String message, final String... args) {
        expect(isNotBlank(string), message, args);
    }

    public static void expect(final boolean condition, final String message, final String... args) {
        if (!condition) {
            throw new IllegalArgumentException(String.format(message, (Object) args));
        }
    }

    public static void stateNotNull(final Object object, final String message, final String... args) {
        state(object != null, message, args);
    }

    public static void stateNotEmpty(final String string, final String message, final String... args) {
        state(isNotBlank(string), message, args);
    }

    public static void state(final boolean condition, final String message, final String... args) {
        if (!condition) {
            throw new IllegalStateException(String.format(message, (Object) args));
        }
    }
}
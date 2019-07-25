package com.naharoo.localizer.exception;

public class LocalizerException extends RuntimeException {

    private static final long serialVersionUID = 1807465974921204890L;

    protected LocalizerException() {
        this(null, null);
    }

    protected LocalizerException(String message) {
        this(message, null);
    }

    protected LocalizerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
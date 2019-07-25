package com.naharoo.localizer.exception;

import com.naharoo.localizer.testutils.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@UnitTest
class LocalizerExceptionTest {

    @Test
    @DisplayName("Exception instance should be constructed without args")
    void localizerExceptionShouldBeThrownWithoutMessageAndCause() {
        // Given
        // LocalizerException

        // When
        try {
            throw new LocalizerException();
        } catch (final LocalizerException e) {
            assertNull(e.getMessage());
            assertNull(e.getCause());
        }

        // Then
        // LocalizerException is thrown
    }

    @Test
    @DisplayName("Exception instance should be constructed with only message")
    void localizerExceptionShouldBeThrownWithMessage() {
        // Given
        final String message = UUID.randomUUID().toString();

        // When
        try {
            throw new LocalizerException(message);
        } catch (final LocalizerException e) {
            assertEquals(message, e.getMessage());
            assertNull(e.getCause());
        }

        // Then
        // LocalizerException is thrown
    }

    @Test
    @DisplayName("Exception instance should be constructed with message and cause")
    void localizerExceptionShouldBeThrownWithMessageAndCause() {
        // Given
        final String message = UUID.randomUUID().toString();
        final RuntimeException cause = new RuntimeException();

        // When
        try {
            throw new LocalizerException(message, cause);
        } catch (final LocalizerException e) {
            assertEquals(message, e.getMessage());
            assertEquals(cause, e.getCause());
        }

        // Then
        // LocalizerException is thrown
    }
}
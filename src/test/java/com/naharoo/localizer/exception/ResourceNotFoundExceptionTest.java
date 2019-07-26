package com.naharoo.localizer.exception;

import com.google.common.collect.ImmutableMap;
import com.naharoo.localizer.testutils.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTest
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("ResourceNotFoundException should be successfully created when given correct class, fieldName and fieldValue")
    void with_correctClassAndFieldNameAndFieldValue() {
        // Given
        final Class<String> resourceClass = String.class;
        final String fieldName = "coder";
        final String fieldValue = "asd1dwd";

        final ImmutableMap<String, Object> expectedIdentifiers = ImmutableMap.<String, Object>builder().put(fieldName, fieldValue).build();

        // When
        final ResourceNotFoundException exception = ResourceNotFoundException.with(resourceClass, fieldName, fieldValue);

        // Then
        assertEquals(resourceClass, exception.getResourceClass());
        assertThat(exception.getIdentifiers()).containsAllEntriesOf(expectedIdentifiers);
    }

    @Test
    @DisplayName("IllegalArgumentException should be thrown when class is null")
    void with_illegalArgs_illegalClass() {
        // Given
        final Class<?> clazz = null;
        final String fieldName = UUID.randomUUID().toString();
        final String fieldValue = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> ResourceNotFoundException.with(clazz, fieldName, fieldValue));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("IllegalArgumentException should be thrown when fieldName is null")
    void with_illegalArgs_illegalFieldName() {
        // Given
        final Class<?> clazz = String.class;
        final String fieldName = null;
        final String fieldValue = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> ResourceNotFoundException.with(clazz, fieldName, fieldValue));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("IllegalArgumentException should be thrown when fieldValue is null")
    void with_illegalArgs_illegalFieldValue() {
        // Given
        final Class<?> clazz = String.class;
        final String fieldName = UUID.randomUUID().toString();
        final Object fieldValue = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> ResourceNotFoundException.with(clazz, fieldName, fieldValue));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("ResourceNotFoundException should be successfully created when given correct class and identifier")
    void with_correctClassAndMap() {
        // Given
        final Class<String> resourceClass = String.class;
        final String fieldName = "coder";
        final String fieldValue = "asd1dwd";

        final ImmutableMap<String, Object> expectedIdentifiers = ImmutableMap.<String, Object>builder().put(fieldName, fieldValue).build();

        // When
        final ResourceNotFoundException exception = ResourceNotFoundException.with(resourceClass, expectedIdentifiers);

        // Then
        assertEquals(resourceClass, exception.getResourceClass());
        assertThat(exception.getIdentifiers()).containsAllEntriesOf(expectedIdentifiers);
    }

    @Test
    @DisplayName("IllegalArgumentException should be thrown when class is null")
    void with_illegalArgs_illegalClass_2() {
        // Given
        final Class<?> clazz = null;
        final Map<String, Object> identifiers = new HashMap<>();

        // When
        assertThrows(IllegalArgumentException.class, () -> ResourceNotFoundException.with(clazz, identifiers));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("IllegalArgumentException should be thrown when class is null")
    void with_illegalArgs_whenIdentifiersMap_isNull() {
        // Given
        final Class<?> clazz = String.class;
        final Map<String, Object> identifiers = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> ResourceNotFoundException.with(clazz, identifiers));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Correct errorMessage should be constructed")
    void constructMessage() {
        // Given
        final Class<String> resourceClass = String.class;
        final String fieldName = "coder";
        final String fieldValue = "asd1dwd";
        final String message = String.format("%s with identifiers [%s: %s] is not found.", resourceClass.getSimpleName(), fieldName, fieldValue);

        final ResourceNotFoundException exception = ResourceNotFoundException.with(resourceClass, fieldName, fieldValue);

        // When
        try {
            throw exception;
        } catch (final ResourceNotFoundException e) {
            // Then
            assertEquals(message, e.getMessage());
        }
    }
}
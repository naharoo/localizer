package com.naharoo.localizer.utils;

import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class AssertionsTest {

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"", "asdasd", "   123   ", "   "})
    @DisplayName("expectNotNull exits normally when input is not null")
    void expectNotNull(final String notNullObject) {
        // Given
        // Not null object

        // When
        Assertions.expectNotNull(notNullObject, "");

        // Then
        // Exits normally
    }

    @Test
    @DisplayName("expectNotNull throws IllegalArgumentException when input is null")
    void expectNotNull_throws() {
        // Given
        final Object object = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> Assertions.expectNotNull(object, ""));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("expect exits normally when input is true")
    void expect() {
        // Given
        final boolean condition = true;

        // When
        Assertions.expect(condition, "");

        // Then
        // Exits normally
    }

    @Test
    @DisplayName("expect throws IllegalArgumentException when input is false")
    void expect_throws() {
        // Given
        final boolean condition = false;

        // When
        assertThrows(IllegalArgumentException.class, () -> Assertions.expect(condition, ""));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"asd", " asd ", "123"})
    @DisplayName("expectNotEmpty should exit normally when input is not empty string")
    void expectNotEmpty(final String input) {
        // Given
        // Not empty string

        // When
        Assertions.expectNotEmpty(input, "");

        // Then
        // Exits normally
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("expectNotEmpty should throw IllegalArgumentException when input is empty string")
    void expectNotEmpty_throws(final String input) {
        // Given
        // Empty value

        // When
        assertThrows(IllegalArgumentException.class, () -> Assertions.expectNotEmpty(input, ""));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("expectNotNullOrEmptyArray should exit normally when input is not empty array")
    void expectNotNullOrEmptyArray() {
        // Given
        final Object[] objects = new Object[]{"asd", 123, 'a', 123L, 123.0F};

        // When
        Assertions.expectNotNullOrEmptyArray(objects, "");

        // Then
        // Exits normally
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("expectNotNullOrEmptyArray should throw IllegalArgumentException when input is null or empty array")
    void expectNotNullOrEmptyArray_throws(final Object[] input) {
        // Given
        // Null or empty array

        // When
        assertThrows(IllegalArgumentException.class, () -> Assertions.expectNotNullOrEmptyArray(input, ""));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"", "asdasd", "   123   ", "   "})
    @DisplayName("expectNotNull exits normally when input is not null")
    void stateNotNull(final String notNullObject) {
        // Given
        // Not null object

        // When
        Assertions.stateNotNull(notNullObject, "");

        // Then
        // Exits normally
    }

    @Test
    @DisplayName("stateNotNull throws IllegalStateException when input is null")
    void stateNotNull_throws() {
        // Given
        final Object object = null;

        // When
        assertThrows(IllegalStateException.class, () -> Assertions.stateNotNull(object, ""));

        // Then
        // IllegalStateException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"asd", " asd ", "123"})
    @DisplayName("expectNotEmpty should exit normally when input is not empty string")
    void stateNotEmpty(final String input) {
        // Given
        // Not empty string

        // When
        Assertions.stateNotEmpty(input, "");

        // Then
        // Exits normally
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("stateNotEmpty should throw IllegalStateException when input is empty string")
    void stateNotEmpty_throws(final String input) {
        // Given
        // Empty value

        // When
        assertThrows(IllegalStateException.class, () -> Assertions.stateNotEmpty(input, ""));

        // Then
        // IllegalStateException is thrown
    }

    @Test
    @DisplayName("expect exits normally when input is true")
    void state() {
        // Given
        final boolean condition = true;

        // When
        Assertions.state(condition, "");

        // Then
        // Exits normally
    }

    @Test
    @DisplayName("state throws IllegalStateException when input is false")
    void state_throws() {
        // Given
        final boolean condition = false;

        // When
        assertThrows(IllegalStateException.class, () -> Assertions.state(condition, ""));

        // Then
        // IllegalStateException is thrown
    }

}
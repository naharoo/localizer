package com.naharoo.localizer.utils;

import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UnitTest
class ConditionsTest {

    private static Stream<Collection> notNullOrEmptyCollections() {
        return Stream.of(
            Arrays.asList(1, 2, 3),
            Sets.newLinkedHashSet("asd", "zxc", "qwe")
        );
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("isBlank should return true when input is blank String")
    void isBlank_true(final String blankString) {
        // Given
        // Blank string

        // When
        final boolean result = Conditions.isBlank(blankString);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"123", "asd", "adk8iu2j3de89", "---", "$$$"})
    @DisplayName("isBlank should return false when input is not blank String")
    void isBlank_false(final String notBlankString) {
        // Given
        // Not blank string

        // When
        final boolean result = Conditions.isBlank(notBlankString);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @ValueSource(strings = {"123", "asd", "adk8iu2j3de89", "---", "$$$"})
    @DisplayName("isNotBlank should return true when input is not blank String")
    void isNotBlank_true(final String notBlankString) {
        // Given
        // Not blank string

        // When
        final boolean result = Conditions.isNotBlank(notBlankString);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("isNotBlank should return false when input is blank String")
    void isNotBlank_false(final String blankString) {
        // Given
        // Blank string

        // When
        final boolean result = Conditions.isNotBlank(blankString);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("isNullOrEmptyArray should return true when input is null or empty array")
    void isNullOrEmptyArray_true(final Object[] array) {
        // Given
        // Null or empty array

        // When
        final boolean result = Conditions.isNullOrEmptyArray(array);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("isNullOrEmptyArray should return false when input is not null or empty array")
    void isNullOrEmptyArray_false() {
        // Given
        final Object[] objects = new Object[]{"asd", 123, 'a', 123L, 123.0F};

        // When
        final boolean result = Conditions.isNullOrEmptyArray(objects);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("isNotNullOrEmptyArray should return true when input is not null or empty array")
    void isNotNullOrEmptyArray_true() {
        // Given
        final Object[] objects = new Object[]{"asd", 123, 'a', 123L, 123.0F};

        // When
        final boolean result = Conditions.isNotNullOrEmptyArray(objects);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("isNotNullOrEmptyArray should return false when input is null or empty array")
    void isNotNullOrEmptyArray_false(final Object[] array) {
        // Given
        // Null or empty array

        // When
        final boolean result = Conditions.isNotNullOrEmptyArray(array);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("isNullOrEmptyCollection should return true when input is null or empty collection")
    void isNullOrEmptyCollection_true(final List nullOrEmptyCollection) {
        // Given
        // Null or empty collection

        // When
        final boolean result = Conditions.isNullOrEmptyCollection(nullOrEmptyCollection);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("notNullOrEmptyCollections")
    @DisplayName("isNullOrEmptyCollection should return false when input is not null or empty collection")
    void iNullOrEmptyCollection_false(final Collection notNullOrEmptyCollection) {
        // Given
        // Not null or empty collection

        // When
        final boolean result = Conditions.isNullOrEmptyCollection(notNullOrEmptyCollection);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("notNullOrEmptyCollections")
    @DisplayName("isNotNullOrEmptyCollection should return true when input is not null or empty collection")
    void isNotNullOrEmptyCollection_true(final Collection notNullOrEmptyCollection) {
        // Given
        // Not null or empty collection

        // When
        final boolean result = Conditions.isNotNullOrEmptyCollection(notNullOrEmptyCollection);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("isNotNullOrEmptyCollection should return false when input is null or empty collection")
    void isNotNullOrEmptyCollection_false(final List nullOrEmptyCollection) {
        // Given
        // Null or empty collection

        // When
        final boolean result = Conditions.isNotNullOrEmptyCollection(nullOrEmptyCollection);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("isNull should return true when input is null object")
    @NullSource
    void isNull_true(final Object nullObject) {
        // Given
        // Null

        // When
        final boolean result = Conditions.isNull(nullObject);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("isNull should return false when input is not null object")
    @ValueSource(strings = {"asdasd", "12s12de23", "__*!@^*@#$("})
    void isNull_false(final Object notNullObject) {
        // Given
        // Not null object

        // When
        final boolean result = Conditions.isNull(notNullObject);

        // Then
        assertFalse(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("isNotNull should return true when input is not null object")
    @ValueSource(strings = {"asdasd", "dd2323d"})
    void isNotNull_true(final Object notNullObject) {
        // Given
        // Not null object

        // When
        final boolean result = Conditions.isNotNull(notNullObject);

        // Then
        assertTrue(result);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("isNotNull should return false when input is null object")
    @NullSource
    void isNotNull_false(final Object nullObject) {
        // Given
        // Null

        // When
        final boolean result = Conditions.isNotNull(nullObject);

        // Then
        assertFalse(result);
    }
}
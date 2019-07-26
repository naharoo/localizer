package com.naharoo.localizer.service.locale.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.repository.LocaleRepository;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import com.naharoo.localizer.testutils.UnitTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@UnitTest
class LocaleServiceImplTest {

    @Mock
    LocaleRepository repository;

    @InjectMocks
    LocaleServiceImpl service;

    static Stream<LocaleCreationRequest> validCreationArguments() {
        return Stream.of(
            LocaleTestHelper.createRandomLocaleCreationRequest(),
            LocaleTestHelper.createRandomLocaleCreationRequest(),
            LocaleTestHelper.createRandomLocaleCreationRequest()
        );
    }

    static Stream<LocaleCreationRequest> illegalCreationArguments() {
        return Stream.of(
            null,
            new LocaleCreationRequest(null, UUID.randomUUID().toString()),
            new LocaleCreationRequest(UUID.randomUUID().toString(), null)
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(repository);
    }

    @MethodSource("validCreationArguments")
    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("Locale should be successfully saved in repository when there is no existing locale with same key")
    void create_success(final LocaleCreationRequest request) {
        // Given
        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        final String key = request.getKey();
        final String name = request.getName();

        doReturn(Optional.empty())
            .when(spy).findByKey(key);

        doAnswer(invocation -> invocation.getArgument(0))
            .when(repository).save(any(Locale.class));

        // When
        final Locale actualResult = spy.create(request);

        // Then
        assertThat(actualResult)
            .isNotNull()
            .isEqualTo(new Locale(key, name));

        verify(spy).findByKey(key);
        verify(repository).save(any(Locale.class));
    }

    @MethodSource("illegalCreationArguments")
    @ParameterizedTest(name = "Input: {arguments}")
    @DisplayName("An IllegalArgumentException should be thrown when Input is not valid")
    void create_illegalArgs(final LocaleCreationRequest request) {
        // Given
        // Illegal Args

        // When
        assertThrows(IllegalArgumentException.class, () -> service.create(request));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("ResourceAlreadyExistsException should be thrown when there is another locale with same key")
    void create_resourceAlreadyExistsException() {
        // Given
        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        final LocaleCreationRequest request = LocaleTestHelper.createRandomLocaleCreationRequest();
        final String key = request.getKey();

        when(spy.findByKey(key))
            .thenReturn(Optional.of(LocaleTestHelper.createRandomLocale()));

        // When
        assertThrows(ResourceAlreadyExistsException.class, () -> service.create(request));

        // Then
        verify(spy).findByKey(key);
    }

    @Test
    @DisplayName("An optional of locale should be escalated from locale lookup strategy")
    void findByKey_success() {
        // Given
        final String key = UUID.randomUUID().toString();

        final Locale locale = LocaleTestHelper.createRandomLocale();
        final Optional<Locale> localeOpt = Optional.of(locale);

        when(repository.findByKeyAndDeletedIsNullIgnoreCase(key))
            .thenReturn(localeOpt);

        // When
        final Optional<Locale> actualOpt = service.findByKey(key);

        // Then
        assertThat(actualOpt)
            .isNotEmpty()
            .get().isEqualTo(locale);
        verify(repository).findByKeyAndDeletedIsNullIgnoreCase(key);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullAndEmptySource
    @DisplayName("IllegalArgumentException should be thrown when input is null or empty string")
    void findByKey(final String key) {
        // Given
        // Null or empty input

        // When
        assertThrows(IllegalArgumentException.class, () -> service.findByKey(key));

        // Then
        // IllegalArgumentException is thrown
    }
}
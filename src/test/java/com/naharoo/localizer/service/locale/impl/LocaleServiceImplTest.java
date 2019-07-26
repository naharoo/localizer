package com.naharoo.localizer.service.locale.impl;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.repository.LocaleRepository;
import com.naharoo.localizer.service.locale.LocaleTestHelper;
import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.naharoo.localizer.utils.PaginationUtils.toPageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

        when(repository.findByKeyIgnoreCaseAndDeletedIsNull(key))
            .thenReturn(localeOpt);

        // When
        final Optional<Locale> actualOpt = service.findByKey(key);

        // Then
        assertThat(actualOpt)
            .isNotEmpty()
            .get().isEqualTo(locale);
        verify(repository).findByKeyIgnoreCaseAndDeletedIsNull(key);
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

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetById should throw IllegalArgumentException when input is not valid")
    void getById_illegalArgs(final String id) {
        // Given
        // Blank id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.getById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetById should throw ResourceNotFoundException when Locale with id is not found")
    void getById_resourceNotFoundException() {
        // Given
        final String id = UUID.randomUUID().toString();

        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        when(spy.findById(id))
            .thenReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));

        // Then
        verify(spy).findById(id);
    }

    @Test
    @DisplayName("GetById should return proper Locale when it is found by id")
    void getById_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String id = locale.getId();

        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        when(spy.findById(id))
            .thenReturn(Optional.of(locale));

        // When
        final Locale actualLocale = service.getById(id);

        // Then
        assertThat(actualLocale)
            .isNotNull()
            .isEqualTo(locale);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("FindById should throw IllegalArgumentException when input is not valid")
    void findById_illegalArgs(final String id) {
        // Given
        // Blank id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.findById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("FindById should return empty Optional when nor locale has been found for id")
    void findById_normalCase() {
        // Given
        final String id = UUID.randomUUID().toString();

        when(repository.findByIdAndDeletedIsNull(id))
            .thenReturn(Optional.empty());

        // When
        final Optional<Locale> localeOpt = service.findById(id);

        // Then
        assertThat(localeOpt)
            .isNotNull()
            .isEmpty();
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @NullSource
    @DisplayName("Search should throw IllegalArgumentException when Input is not valid")
    void search_illegalArgs(final LocaleSearchRequest request) {
        // Given
        // Illegal Input

        // When
        assertThrows(IllegalArgumentException.class, () -> service.search(request));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Search should return proper data when input is correct")
    void search_normalCase() {
        // Given
        final LocaleSearchRequest searchRequest = LocaleTestHelper.createRandomLocaleSearchRequest();
        final PageRequest pageRequest = toPageRequest(
            searchRequest.getFrom(),
            searchRequest.getSize(),
            searchRequest.getSortField(),
            searchRequest.getSortOrder()
        );

        final List<Locale> expectedLocales = Arrays.asList(
            LocaleTestHelper.createRandomLocale(),
            LocaleTestHelper.createRandomLocale(),
            LocaleTestHelper.createRandomLocale()
        );
        final int expectedTotalItems = expectedLocales.size();

        when(repository.search(pageRequest))
            .thenReturn(new PageImpl<>(expectedLocales, pageRequest, expectedTotalItems));

        // When
        final GenericListResponse<Locale> response = service.search(searchRequest);

        // Then
        assertNotNull(response);
        final List<Locale> actualLocales = response.getItems();
        assertThat(actualLocales)
            .isNotNull()
            .isNotEmpty()
            .containsExactlyElementsOf(expectedLocales);
        final long actualTotalItems = response.getTotalItems();
        assertEquals(expectedTotalItems, actualTotalItems);

        verify(repository).search(pageRequest);
    }
}
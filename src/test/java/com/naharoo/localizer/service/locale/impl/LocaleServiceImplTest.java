package com.naharoo.localizer.service.locale.impl;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.locale.LocaleCreationRequest;
import com.naharoo.localizer.domain.locale.LocaleModificationRequest;
import com.naharoo.localizer.domain.locale.LocaleSearchRequest;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.repository.LocaleRepository;
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

import java.time.LocalDateTime;
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

    @Test
    @DisplayName("An IllegalArgumentException should be thrown when Input is null")
    void create_illegalArgs() {
        // Given
        // Illegal Args

        // When
        assertThrows(IllegalArgumentException.class, () -> service.create(null));

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
        verify(spy).findById(id);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetByKey should throw IllegalArgumentException when input is not valid")
    void getByKey_illegalArgs(final String key) {
        // Given
        // Blank key

        // When
        assertThrows(IllegalArgumentException.class, () -> service.getByKey(key));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetByKey should throw ResourceNotFoundException when Locale with key is not found")
    void getByKey_resourceNotFoundException() {
        // Given
        final String key = UUID.randomUUID().toString();

        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        when(spy.findByKey(key))
            .thenReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> service.getByKey(key));

        // Then
        verify(spy).findByKey(key);
    }

    @Test
    @DisplayName("GetByKey should return proper Locale when it is found by key")
    void getByKey_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String key = locale.getId();

        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        when(spy.findByKey(key))
            .thenReturn(Optional.of(locale));

        // When
        final Locale actualLocale = service.getByKey(key);

        // Then
        assertThat(actualLocale)
            .isNotNull()
            .isEqualTo(locale);
        verify(spy).findByKey(key);
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
        verify(repository).findByIdAndDeletedIsNull(id);
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
        final String query = searchRequest.getQuery();
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

        when(repository.search(query, pageRequest))
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

        verify(repository).search(query, pageRequest);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("Delete should throw IllegalArgumentException when input is not valid")
    void delete_illegalArgs(final String id) {
        // Given
        // Blank id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.delete(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Delete should return proper Locale when it is found by id")
    void delete_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String id = locale.getId();

        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        doReturn(locale)
            .when(spy).getById(id);
        doAnswer(invocation -> invocation.getArgument(0))
            .when(repository).save(any(Locale.class));

        // When
        final Locale actualLocale = spy.delete(id);

        // Then
        assertThat(actualLocale)
            .isNotNull()
            .isEqualToIgnoringGivenFields(locale, "updated", "deleted");
        final LocalDateTime updated = actualLocale.getUpdated();
        assertNotNull(updated);
        final LocalDateTime deleted = actualLocale.getDeleted();
        assertNotNull(deleted);

        verify(spy).getById(id);
        verify(repository).save(any(Locale.class));
    }

    @Test
    @DisplayName("Update should throw IllegalArgumentException when input is null")
    void update_illegalArgs() {
        // Given
        // Illegal Input

        // When
        assertThrows(IllegalArgumentException.class, () -> service.update(null));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Update should throw ResourceNotFoundException when Locale with provided id is not found")
    void update_resourceNotFoundException() {
        // Given
        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        final LocaleModificationRequest localeModificationRequest = LocaleTestHelper.createRandomLocaleModificationRequest();
        final String id = localeModificationRequest.getId();

        doThrow(ResourceNotFoundException.class)
            .when(spy).getById(id);

        // When
        assertThrows(ResourceNotFoundException.class, () -> spy.update(localeModificationRequest));

        // Then
        verify(spy).getById(id);
        verify(spy).update(localeModificationRequest);
        verifyNoMoreInteractions(spy);
    }

    @Test
    @DisplayName("Update should normally execute and return modified Locale when input is correct")
    void update_normalCase() {
        // Given
        final LocaleServiceImpl spy = spy(new LocaleServiceImpl(repository));

        final LocaleModificationRequest localeModificationRequest = LocaleTestHelper.createRandomLocaleModificationRequest();
        final String id = localeModificationRequest.getId();
        final String key = localeModificationRequest.getKey();
        final String name = localeModificationRequest.getName();

        final Locale locale = LocaleTestHelper.createRandomLocale();

        doReturn(locale)
            .when(spy).getById(id);
        doAnswer(invocation -> invocation.getArgument(0))
            .when(repository).save(any(Locale.class));

        // When
        final Locale updatedLocale = spy.update(localeModificationRequest);

        // Then
        assertThat(updatedLocale)
            .isNotNull()
            .isEqualToIgnoringGivenFields(locale, "key", "name", "updated");
        assertEquals(key, updatedLocale.getKey());
        assertEquals(name, updatedLocale.getName());

        verify(spy).getById(id);
        verify(spy).update(localeModificationRequest);
        verify(repository).save(any(Locale.class));
        verifyNoMoreInteractions(spy);
    }
}
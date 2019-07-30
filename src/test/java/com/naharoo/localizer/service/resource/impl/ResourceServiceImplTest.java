package com.naharoo.localizer.service.resource.impl;

import com.naharoo.localizer.domain.locale.Locale;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.repository.ResourceRepository;
import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@UnitTest
class ResourceServiceImplTest {

    @Mock
    ResourceRepository repository;

    @InjectMocks
    ResourceServiceImpl service;

    static Stream<Locale> illegalLocalesForDelete() {
        return Stream.of(
            null,
            LocaleTestHelper.createLocale(
                null,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocaleTestHelper.createLocale(
                "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocaleTestHelper.createLocale(
                "   ",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocaleTestHelper.createLocale(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
            )
        );
    }

    static Stream<Locale> illegalLocales() {
        return Stream.of(
            null,
            LocaleTestHelper.createLocale(
                null,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocaleTestHelper.createLocale(
                "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            LocaleTestHelper.createLocale(
                "   ",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            repository
        );
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("Delete Resource should throw IllegalArgumentException when input is not valid")
    void delete_illegalArgs(final String id) {
        // Given
        // Empty Id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.delete(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Delete Resource should execute normally and return deleted Resource when input is valid")
    void delete_normalCase() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final Resource resource = ResourceTestHelper.createRandomResource();
        final String id = resource.getId();

        doReturn(resource)
            .when(serviceSpy).getById(id);
        doAnswer(invocation -> invocation.getArgument(0))
            .when(repository).save(any(Resource.class));

        // When
        final Resource modifiedResource = serviceSpy.delete(id);

        // Then
        assertThat(modifiedResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(resource, "updated", "updated");
        assertNotNull(modifiedResource.getUpdated());
        assertNotNull(modifiedResource.getDeleted());

        verify(serviceSpy).delete(id);
        verify(serviceSpy).getById(id);
        verify(repository).save(any(Resource.class));
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("Delete Resource should throw ResourceNotFoundException when resource with provided id is not found")
    void delete_resourceNotFoundException() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));
        final String id = UUID.randomUUID().toString();

        doThrow(ResourceNotFoundException.class)
            .when(serviceSpy).getById(id);

        // When
        assertThrows(ResourceNotFoundException.class, () -> serviceSpy.delete(id));

        // Then
        verify(serviceSpy).getById(id);
        verify(serviceSpy).delete(id);
        verifyNoMoreInteractions(serviceSpy);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetById Resource should throw IllegalArgumentException when input is not valid")
    void getById_illegalArgs(final String id) {
        // Given
        // Empty Id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.getById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetById Resource should throw ResourceNotFoundException when resource with provided id is not found")
    void getById_resourceNotFoundException() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));
        final String id = UUID.randomUUID().toString();

        doReturn(Optional.empty())
            .when(serviceSpy).findById(id);

        // When
        assertThrows(ResourceNotFoundException.class, () -> serviceSpy.getById(id));

        // Then
        verify(serviceSpy).getById(id);
        verify(serviceSpy).findById(id);
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("GetById Resource should execute normally and return found resource when input is valid")
    void getById_normalCase() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final Resource resource = ResourceTestHelper.createRandomResource();
        final String id = resource.getId();

        doReturn(Optional.of(resource))
            .when(serviceSpy).findById(id);

        // When
        final Resource actualResource = serviceSpy.getById(id);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isEqualTo(resource);

        verify(serviceSpy).getById(id);
        verify(serviceSpy).findById(id);
        verifyNoMoreInteractions(serviceSpy);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("FindById Resource should throw IllegalArgumentException when input is not valid")
    void findById_illegalArgs(final String id) {
        // Given
        // Empty Id

        // When
        assertThrows(IllegalArgumentException.class, () -> service.findById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("FindById Resource should escalate repository result")
    void findById_normalCase() {
        // Given
        final Resource resource = ResourceTestHelper.createRandomResource();
        final String id = resource.getId();

        when(repository.findByIdAndDeletedIsNull(id))
            .thenReturn(Optional.of(resource));

        // When
        final Optional<Resource> actualOpt = service.findById(id);

        // Then
        assertThat(actualOpt)
            .isNotNull()
            .get()
            .isNotNull()
            .isEqualTo(resource);
        verify(repository).findByIdAndDeletedIsNull(id);
    }

    @Test
    @DisplayName("Update Resource should throw IllegalArgumentException when input is null")
    void update_illegalArgs() {
        // Given
        final ResourceModificationRequest modificationRequest = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> service.update(modificationRequest));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Update Resource should throw ResourceNotFoundException when no resource has been found for provided id")
    void update_resourceNotFoundException() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final ResourceModificationRequest modificationRequest = ResourceTestHelper.createRandomResourceModificationRequest();
        final String id = modificationRequest.getId();

        doThrow(ResourceNotFoundException.class)
            .when(serviceSpy).getById(id);

        // When
        assertThrows(ResourceNotFoundException.class, () -> serviceSpy.update(modificationRequest));

        // Then
        verify(serviceSpy).update(modificationRequest);
        verify(serviceSpy).getById(id);
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("Update Resource should execute normally and return updated Resource when input is valid")
    void update_normalCase() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final ResourceModificationRequest modificationRequest = ResourceTestHelper.createRandomResourceModificationRequest();
        final String id = modificationRequest.getId();

        final Resource expectedResource = ResourceTestHelper.createRandomResource();

        doReturn(expectedResource)
            .when(serviceSpy).getById(id);
        doAnswer(invocation -> invocation.getArgument(0))
            .when(repository).save(any(Resource.class));

        // When
        final Resource actualResource = serviceSpy.update(modificationRequest);

        // Then
        assertNotNull(actualResource);
        assertEquals(expectedResource.getId(), actualResource.getId());
        assertEquals(expectedResource.getLocale(), actualResource.getLocale());
        assertEquals(modificationRequest.getKey(), actualResource.getKey());
        assertEquals(modificationRequest.getValue(), actualResource.getValue());
        assertNotNull(actualResource.getDeleted());
        assertNotNull(actualResource.getUpdated());

        verify(serviceSpy).update(modificationRequest);
        verify(serviceSpy).getById(id);
        verify(repository).save(any(Resource.class));
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("DeleteByLocale should throw IllegalArgumentException when deleted is null")
    void deleteByLocale_illegalDeleted() {
        // Given
        final LocalDateTime deleted = null;
        final Locale locale = LocaleTestHelper.createRandomLocale();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.deleteByLocale(locale, deleted));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocalesForDelete")
    @DisplayName("DeleteByLocale should throw IllegalArgumentException when provided Locale is not valid")
    void deleteByLocale_IllegalLocale(final Locale locale) {
        // Given
        final LocalDateTime deleted = LocalDateTime.now();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.deleteByLocale(locale, deleted));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("DeleteByLocale should delegate localeId and deleted to repository and remove all resources")
    void deleteByLocale_normalCase() {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String localeId = locale.getId();
        final LocalDateTime deleted = locale.getDeleted();

        doNothing()
            .when(repository).deleteByLocaleId(localeId, deleted);

        // When
        service.deleteByLocale(locale, deleted);

        // Then
        verify(repository).deleteByLocaleId(localeId, deleted);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("Create Resource should throw IllegalArgumentException when input key is not valid")
    void create_illegalKey(final String key) {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String value = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.create(key, locale, value));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("Create Resource should throw IllegalArgumentException when input value is not valid")
    void create_illegalValue(final String value) {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String key = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.create(key, locale, value));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocales")
    @DisplayName("Create Resource should throw IllegalArgumentException when input locale is not valid")
    void create_illegalLocale(final Locale locale) {
        // Given
        final String key = UUID.randomUUID().toString();
        final String value = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.create(key, locale, value));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Create Resource should throw ResourceAlreadyExistsException when another resource with same key and locale already exists")
    void create_resourceAlreadyExistsException() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final String key = UUID.randomUUID().toString();
        final String value = UUID.randomUUID().toString();
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final Resource resource = ResourceTestHelper.createRandomResource();

        doReturn(Optional.of(resource))
            .when(serviceSpy).findByKeyAndLocale(key, locale);

        // When
        assertThrows(ResourceAlreadyExistsException.class, () -> serviceSpy.create(key, locale, value));

        // Then
        verify(serviceSpy).create(key, locale, value);
        verify(serviceSpy).findByKeyAndLocale(key, locale);
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("Create Resource should successfully call repos save method with new locale data when input is valid")
    void create_normalCase() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final String key = UUID.randomUUID().toString();
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String value = UUID.randomUUID().toString();

        doReturn(Optional.empty())
            .when(serviceSpy).findByKeyAndLocale(key, locale);
        doAnswer(invocation -> {
            final Resource resource = invocation.getArgument(0);

            final LocalDateTime currentDateTime = LocalDateTime.now();
            resource.setCreated(currentDateTime);
            resource.setUpdated(currentDateTime);

            return resource;
        })
            .when(repository).save(any(Resource.class));

        // When
        final Resource actualResource = serviceSpy.create(key, locale, value);

        // Then
        assertNotNull(actualResource);
        assertEquals(key, actualResource.getKey());
        assertEquals(locale, actualResource.getLocale());
        assertEquals(value, actualResource.getValue());
        assertNotNull(actualResource.getCreated());
        assertNotNull(actualResource.getUpdated());

        verify(serviceSpy).create(key, locale, value);
        verify(serviceSpy).findByKeyAndLocale(key, locale);
        verify(repository).save(any(Resource.class));
        verifyNoMoreInteractions(serviceSpy);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("FindByKeyAndLocale Resource should throw IllegalArgumentException when input key is empty")
    void findByKeyAndLocale_illegalKey(final String key) {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.findByKeyAndLocale(key, locale));

        // Then
        // IllegalArgumentException is thrown
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocales")
    @DisplayName("FindByKeyAndLocale Resource should throw IllegalArgumentException when input locale is not valid")
    void findByKeyAndLocale_illegalLocale(final Locale locale) {
        // Given
        final String key = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.findByKeyAndLocale(key, locale));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("FindByKeyAndLocale Resource should delegate Optional from repo when input is valid")
    void findByKeyAndLocale_normalCase() {
        // Given
        final String key = UUID.randomUUID().toString();
        final Locale locale = LocaleTestHelper.createRandomLocale();
        final Resource resource = ResourceTestHelper.createRandomResource();

        when(repository.findByKeyIgnoreCaseAndLocaleAndDeletedIsNull(key, locale))
            .thenReturn(Optional.of(resource));

        // When
        final Optional<Resource> actualResourceOpt = service.findByKeyAndLocale(key, locale);

        // Then
        assertThat(actualResourceOpt)
            .isNotNull()
            .get()
            .isSameAs(resource);
        verify(repository).findByKeyIgnoreCaseAndLocaleAndDeletedIsNull(key, locale);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetByKeyAndLocale should throw IllegalArgumentException when provided key is empty")
    void getByKeyAndLocale_illegalKey(final String key) {
        // Given
        final Locale locale = LocaleTestHelper.createRandomLocale();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.getByKeyAndLocale(key, locale));

        // Then
        // IllegalArgumentException
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalLocales")
    @DisplayName("GetByKeyAndLocale should throw IllegalArgumentException when provided locale is not valid")
    void getByKeyAndLocale_illegalLocale(final Locale locale) {
        // Given
        final String key = UUID.randomUUID().toString();

        // When
        assertThrows(IllegalArgumentException.class, () -> service.getByKeyAndLocale(key, locale));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetByKeyAndLocale should throw ResourceNotFoundException when FindByKeyAndLocale return empty optional")
    void getByKeyAndLocale_resourceNotFound() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final Locale locale = LocaleTestHelper.createRandomLocale();
        final String key = UUID.randomUUID().toString();

        doReturn(Optional.empty())
            .when(serviceSpy).findByKeyAndLocale(key, locale);

        // When
        assertThrows(ResourceNotFoundException.class, () -> serviceSpy.getByKeyAndLocale(key, locale));

        // Then
        verify(serviceSpy).getByKeyAndLocale(key, locale);
        verify(serviceSpy).findByKeyAndLocale(key, locale);
        verifyNoMoreInteractions(serviceSpy);
    }

    @Test
    @DisplayName("GetResourceByKeyAndLocaleKey should execute normally and return found Resource when input is valid and resource exists")
    void getByKeyAndLocale_normalCase() {
        // Given
        final ResourceServiceImpl serviceSpy = spy(new ResourceServiceImpl(repository));

        final Locale locale = LocaleTestHelper.createRandomLocale();
        final Resource resource = ResourceTestHelper.createRandomResource();
        final String key = resource.getKey();

        doReturn(Optional.of(resource))
            .when(serviceSpy).findByKeyAndLocale(key, locale);

        // When
        final Resource actualResource = serviceSpy.getByKeyAndLocale(key, locale);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isSameAs(resource);

        verify(serviceSpy).getByKeyAndLocale(key, locale);
        verify(serviceSpy).findByKeyAndLocale(key, locale);
        verifyNoMoreInteractions(serviceSpy);
    }
}
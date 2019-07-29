package com.naharoo.localizer.service.resource.impl;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.repository.ResourceRepository;
import com.naharoo.localizer.testutils.UnitTest;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@UnitTest
class ResourceServiceImplTest {

    @Mock
    ResourceRepository repository;

    @InjectMocks
    ResourceServiceImpl service;

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

}
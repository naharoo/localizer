package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.resource.ResourceService;
import com.naharoo.localizer.testutils.source.EmptyStringSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteResourceEndpointImplTest extends AbstractEndpointTest {

    @Mock
    ResourceService service;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(service);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("Delete Resource should throw IllegalArgumentException when provided id is empty")
    void delete_illegalArgs(final String id) {
        // Given
        // Illegal Args

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.delete(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Delete Resource should process normally and return deleted Resource when input is valid")
    void delete_normalCase() {
        // Given
        final Resource resource = ResourceTestHelper.createRandomResource();
        final String id = resource.getId();

        when(service.delete(id))
            .thenReturn(resource);

        // When
        final ResourceDto actualResource = endpoint.delete(id);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(resource, "locale");
        assertThat(actualResource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(resource.getLocale());
        verify(service).delete(id);
        verify(mapper).map(resource, ResourceDto.class);
    }
}

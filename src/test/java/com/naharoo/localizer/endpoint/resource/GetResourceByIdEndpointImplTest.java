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

class GetResourceByIdEndpointImplTest extends AbstractEndpointTest {

    @Mock
    ResourceService service;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            service
        );
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @EmptyStringSource
    @DisplayName("GetById should throw IllegalArgumentException when id is empty")
    void getById_illegalArgs(final String id) {
        // Given
        // Illegal Input

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.getById(id));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("GetById should execute normally and return result gotten from service")
    void getById_normalCase() {
        // Given
        final Resource expectedResource = ResourceTestHelper.createRandomResource();
        final String id = expectedResource.getId();

        doReturn(expectedResource)
            .when(service).getById(id);

        // When
        final ResourceDto actualResource = endpoint.getById(id);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(expectedResource, "locale");
        assertThat(actualResource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(expectedResource.getLocale());

        verify(service).getById(id);
        verify(mapper).map(expectedResource, ResourceDto.class);
    }
}
package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.ResourceManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateResourceEndpointImplTest extends AbstractEndpointTest {

    @Mock
    ResourceManager manager;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    static Stream<ResourceCreationRequestDto> illegalCreationRequest() {
        return Stream.of(
            null,
            new ResourceCreationRequestDto(null, UUID.randomUUID().toString(), UUID.randomUUID().toString()),
            new ResourceCreationRequestDto(UUID.randomUUID().toString(), null, UUID.randomUUID().toString()),
            new ResourceCreationRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null)
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            manager
        );
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalCreationRequest")
    @DisplayName("Create should throw IllegalArgumentException when input is not valid")
    void create_illegalArgs(final ResourceCreationRequestDto creationRequestDto) {
        // Given
        // Illegal Input

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.create(creationRequestDto));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Create should execute normally and return result gotten from manager")
    void create_normalCase() {
        // Given
        final ResourceCreationRequestDto creationRequestDto = ResourceTestHelper.createRandomResourceCreationRequestDto();
        final Resource expectedResource = ResourceTestHelper.createRandomResource();

        doReturn(expectedResource)
            .when(manager).createResource(any(ResourceCreationRequest.class));

        // When
        final ResourceDto actualResource = endpoint.create(creationRequestDto);

        // Then
        assertThat(actualResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(expectedResource, "locale");
        assertThat(actualResource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(expectedResource.getLocale());

        verify(mapper).map(creationRequestDto, ResourceCreationRequest.class);
        verify(manager).createResource(any(ResourceCreationRequest.class));
        verify(mapper).map(expectedResource, ResourceDto.class);
    }
}
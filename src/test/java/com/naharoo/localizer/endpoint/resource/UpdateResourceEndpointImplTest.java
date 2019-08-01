package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.resource.ResourceService;
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

public class UpdateResourceEndpointImplTest extends AbstractEndpointTest {

    @Mock
    ResourceService service;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    private static Stream<ResourceModificationRequestDto> illegalArgs() {
        return Stream.of(
            null,
            ResourceTestHelper.createResourceModificationRequestDto(null, UUID.randomUUID().toString(), UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto("", UUID.randomUUID().toString(), UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto("  ", UUID.randomUUID().toString(), UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), null, UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), "", UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), "  ", UUID.randomUUID().toString()),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), ""),
            ResourceTestHelper.createResourceModificationRequestDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "  ")
        );
    }

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(service);
    }

    @ParameterizedTest(name = "Input: {arguments}")
    @MethodSource("illegalArgs")
    @DisplayName("Update Resource should throw IllegalArgumentException when input is not valid")
    void update_illegalArgs(final ResourceModificationRequestDto modificationRequestDto) {
        // Given
        // Illegal args

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.update(modificationRequestDto));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Update Resource should process normally and return updated Resource when input is valid")
    void update_normalCase() {
        // Given
        final ResourceModificationRequestDto modificationRequestDto = ResourceTestHelper.createRandomResourceModificationRequestDto();
        final Resource resource = ResourceTestHelper.createRandomResource();

        when(service.update(any(ResourceModificationRequest.class)))
            .thenReturn(resource);

        // When
        final ResourceDto updatedResource = endpoint.update(modificationRequestDto);

        // Then
        assertThat(updatedResource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(resource, "locale");
        assertThat(updatedResource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(resource.getLocale());
        verify(mapper).map(modificationRequestDto, ResourceModificationRequest.class);
        verify(service).update(any(ResourceModificationRequest.class));
        verify(mapper).map(resource, ResourceDto.class);
    }
}

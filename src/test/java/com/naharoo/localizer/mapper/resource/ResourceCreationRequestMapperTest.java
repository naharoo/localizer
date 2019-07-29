package com.naharoo.localizer.mapper.resource;

import com.naharoo.localizer.domain.resource.ResourceCreationRequest;
import com.naharoo.localizer.endpoint.resource.ResourceCreationRequestDto;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceCreationRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("ResourceCreationRequest should be successfully mapped to ResourceCreationRequestDto")
    void resourceCreationRequestToResourceCreationRequestDto() {
        // Given
        final ResourceCreationRequest resourceCreationRequest = ResourceTestHelper.createRandomResourceCreationRequest();

        // When
        final ResourceCreationRequestDto resourceCreationRequestDto = mapper.map(resourceCreationRequest, ResourceCreationRequestDto.class);

        // Then
        assertThat(resourceCreationRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceCreationRequest);
    }

    @Test
    @DisplayName("ResourceCreationRequestDto should be successfully mapped to ResourceCreationRequest")
    void resourceCreationRequestDtoToResourceCreationRequest() {
        // Given
        final ResourceCreationRequestDto resourceCreationRequestDto = ResourceTestHelper.createRandomResourceCreationRequestDto();

        // When
        final ResourceCreationRequest resourceCreationRequest = mapper.map(resourceCreationRequestDto, ResourceCreationRequest.class);

        // Then
        assertThat(resourceCreationRequest)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceCreationRequestDto);
    }
}

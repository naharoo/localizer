package com.naharoo.localizer.mapper.resource;

import com.naharoo.localizer.domain.resource.ResourceModificationRequest;
import com.naharoo.localizer.endpoint.resource.ResourceModificationRequestDto;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceModificationRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("ResourceModificationRequest should be successfully mapped to ResourceCreationRequestDto")
    void resourceModificationRequestToResourceModificationRequestDto() {
        // Given
        final ResourceModificationRequest resourceModificationRequest = ResourceTestHelper.createRandomResourceModificationRequest();

        // When
        final ResourceModificationRequestDto resourceModificationRequestDto = mapper.map(resourceModificationRequest, ResourceModificationRequestDto.class);

        // Then
        assertThat(resourceModificationRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceModificationRequest);
    }

    @Test
    @DisplayName("ResourceModificationRequestDto should be successfully mapped to ResourceModificationRequest")
    void resourceModificationRequestDtoToResourceModificationRequest() {
        // Given
        final ResourceModificationRequestDto resourceModificationRequestDto = ResourceTestHelper.createRandomResourceModificationRequestDto();

        // When
        final ResourceModificationRequest resourceModificationRequest = mapper.map(resourceModificationRequestDto, ResourceModificationRequest.class);

        // Then
        assertThat(resourceModificationRequest)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceModificationRequestDto);
    }
}

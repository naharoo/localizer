package com.naharoo.localizer.mapper.resource;

import com.naharoo.localizer.domain.resource.ResourceSearchRequest;
import com.naharoo.localizer.endpoint.resource.ResourceSearchRequestDto;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceSearchRequestMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("ResourceSearchRequest should be successfully mapped to ResourceSearchRequestDto")
    void resourceSearchRequestToResourceSearchRequestDto() {
        // Given
        final ResourceSearchRequest resourceSearchRequest = ResourceTestHelper.createRandomResourceSearchRequest();

        // When
        final ResourceSearchRequestDto resourceSearchRequestDto = mapper.map(resourceSearchRequest, ResourceSearchRequestDto.class);

        // Then
        assertThat(resourceSearchRequestDto)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceSearchRequest);
    }

    @Test
    @DisplayName("ResourceSearchRequestDto should be successfully mapped to ResourceSearchRequest")
    void resourceSearchRequestDtoToResourceSearchRequest() {
        // Given
        final ResourceSearchRequestDto resourceSearchRequestDto = ResourceTestHelper.createRandomResourceSearchRequestDto();

        // When
        final ResourceSearchRequest resourceSearchRequest = mapper.map(resourceSearchRequestDto, ResourceSearchRequest.class);

        // Then
        assertThat(resourceSearchRequest)
            .isNotNull()
            .isEqualToComparingFieldByField(resourceSearchRequestDto);
    }
}

package com.naharoo.localizer.mapper.resource;

import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.endpoint.resource.ResourceDto;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.mapper.AbstractMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMapperTest extends AbstractMapperTest {

    @Test
    @DisplayName("Resource should be successfully mapped to ResourceDto")
    void resourceToResourceDto() {
        // Given
        final Resource resource = ResourceTestHelper.createRandomResource();

        // When
        final ResourceDto resourceDto = mapper.map(resource, ResourceDto.class);

        // Then
        assertThat(resourceDto)
            .isNotNull()
            .isEqualToIgnoringGivenFields(resource, "locale");
        assertThat(resourceDto.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(resource.getLocale());
    }

    @Test
    @DisplayName("ResourceDto should be successfully mapped to Resource")
    void resourceDtoToResource() {
        // Given
        final ResourceDto resourceDto = ResourceTestHelper.createRandomResourceDto();

        // When
        final Resource resource = mapper.map(resourceDto, Resource.class);

        // Then
        assertThat(resource)
            .isNotNull()
            .isEqualToIgnoringGivenFields(resourceDto, "locale");
        assertThat(resource.getLocale())
            .isNotNull()
            .isEqualToComparingFieldByField(resourceDto.getLocale());
    }
}

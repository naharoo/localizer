package com.naharoo.localizer.endpoint.resource;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.domain.resource.Resource;
import com.naharoo.localizer.domain.resource.ResourceSearchRequest;
import com.naharoo.localizer.endpoint.AbstractEndpointTest;
import com.naharoo.localizer.helper.ResourceTestHelper;
import com.naharoo.localizer.service.resource.ResourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SearchResourcesEndpointImplTest extends AbstractEndpointTest {

    @Mock
    ResourceService service;

    @InjectMocks
    ResourcesEndpointImpl endpoint;

    @AfterEach
    void tearDown() {
        validateMockitoUsage();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Search Resources should throw IllegalArgumentException when searchRequest is null")
    void search_illegalArgs() {
        // Given
        final ResourceSearchRequestDto searchRequestDto = null;

        // When
        assertThrows(IllegalArgumentException.class, () -> endpoint.search(searchRequestDto));

        // Then
        // IllegalArgumentException is thrown
    }

    @Test
    @DisplayName("Search Resources should return found Resources when searchRequest is valid")
    void search_normalCase() {
        // Given
        final ResourceSearchRequestDto searchRequestDto = ResourceTestHelper.createRandomResourceSearchRequestDto();

        final List<Resource> expectedItems = Arrays.asList(
            ResourceTestHelper.createRandomResource(),
            ResourceTestHelper.createRandomResource(),
            ResourceTestHelper.createRandomResource()
        );
        final int expectedTotalItems = expectedItems.size();
        final GenericListResponse<Resource> expectedResponse = new GenericListResponse<>(expectedItems, expectedTotalItems);

        when(service.search(any(ResourceSearchRequest.class)))
            .thenReturn(expectedResponse);

        // When
        final GenericListResponse<ResourceDto> actualResponse = endpoint.search(searchRequestDto);

        // Then
        assertNotNull(actualResponse);
        final List<ResourceDto> actualItems = actualResponse.getItems();
        assertThatListsAreEqualIgnoringFields(expectedItems, actualItems, "locale");
        assertThatListsAreEqualIgnoringFields(
            expectedItems.stream().map(Resource::getLocale).collect(Collectors.toList()),
            actualItems.stream().map(ResourceDto::getLocale).collect(Collectors.toList())
        );
        final long actualTotalItems = actualResponse.getTotalItems();
        assertEquals(expectedTotalItems, actualTotalItems);

        verify(mapper).map(searchRequestDto, ResourceSearchRequest.class);
        verify(service).search(any(ResourceSearchRequest.class));
        verify(mapper).mapAsList(expectedItems, ResourceDto.class);
    }
}

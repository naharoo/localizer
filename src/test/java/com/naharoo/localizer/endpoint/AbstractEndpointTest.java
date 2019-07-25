package com.naharoo.localizer.endpoint;

import com.naharoo.localizer.mapper.BeanMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@Tag("endpoint")
@ExtendWith(MockitoExtension.class)
public abstract class AbstractEndpointTest {

    @Spy
    protected BeanMapper mapper;

    @AfterEach
    void validateMapperUsage() {
        validateMockitoUsage();
        verifyNoMoreInteractions(
            mapper
        );
    }
}


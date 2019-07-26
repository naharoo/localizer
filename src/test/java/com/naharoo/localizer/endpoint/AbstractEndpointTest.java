package com.naharoo.localizer.endpoint;

import com.naharoo.localizer.mapper.BeanMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    protected void assertListAreFieldByFieldEqual(final List expected, final List actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i))
                .isEqualToComparingFieldByField(expected.get(i));
        }
    }
}


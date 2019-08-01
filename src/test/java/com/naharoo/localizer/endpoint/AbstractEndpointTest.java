package com.naharoo.localizer.endpoint;

import com.naharoo.localizer.mapper.BeanMapper;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    protected void assertThatListsAreEqualIgnoringFields(final List expected, final List actual, final String... fieldNames) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            final ObjectAssert<Object> assertion = assertThat(actual.get(i));
            if (fieldNames == null) {
                assertion.isEqualToComparingFieldByField(expected.get(i));
            } else {
                assertion.isEqualToIgnoringGivenFields(expected.get(i), fieldNames);
            }
        }
    }
}


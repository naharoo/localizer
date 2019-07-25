package com.naharoo.localizer.mapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("mapper")
@ExtendWith(MockitoExtension.class)
public abstract class AbstractMapperTest {

    protected final BeanMapper mapper = new BeanMapper();
}

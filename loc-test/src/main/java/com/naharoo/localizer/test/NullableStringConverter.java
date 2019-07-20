package com.naharoo.localizer.test;

import org.junit.jupiter.params.converter.DefaultArgumentConverter;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class NullableStringConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(final Object source, final Class<?> targetType) {
        if ("null".equals(source)) {
            return null;
        }
        return DefaultArgumentConverter.INSTANCE.convert(source, targetType);
    }
}

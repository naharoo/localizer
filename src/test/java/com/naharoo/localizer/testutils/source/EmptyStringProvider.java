package com.naharoo.localizer.testutils.source;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EmptyStringProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        Class<?>[] parameterTypes = testMethod.getParameterTypes();

        Preconditions.condition(parameterTypes.length > 0, () -> String.format("@EmptyStringSource cannot provide an empty argument to method [%s]: the method does not declare any formal parameters.", testMethod.toGenericString()));

        Class<?> parameterType = parameterTypes[0];

        if (!String.class.equals(parameterType)) {
            throw new PreconditionViolationException(String.format("@EmptyStringSource cannot provide an empty argument to method [%s]: [%s] is not a supported type.", testMethod.toGenericString(), parameterType.getName()));
        }

        return Stream.of(arguments(""), arguments("   "));
    }
}
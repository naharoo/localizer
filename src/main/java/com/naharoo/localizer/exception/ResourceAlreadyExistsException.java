package com.naharoo.localizer.exception;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.stream.Collectors;

import static com.naharoo.localizer.utils.Assertions.expectNotEmpty;
import static com.naharoo.localizer.utils.Assertions.expectNotNull;

public class ResourceAlreadyExistsException extends LocalizerException {

    private static final long serialVersionUID = 3442752536376056192L;

    private final Class<?> resourceClass;
    private final transient Map<String, Object> identifiers;

    private ResourceAlreadyExistsException(final Class<?> resourceClass, final Map<String, Object> identifiers) {
        super(constructMessage(resourceClass, identifiers));
        this.resourceClass = resourceClass;
        this.identifiers = identifiers;
    }

    public static ResourceAlreadyExistsException with(final Class<?> resourceClass, final String fieldName, final Object fieldValue) {
        expectNotNull(resourceClass, "resourceClass cannot be null.");
        expectNotEmpty(fieldName, "fieldName cannot be null.");
        expectNotNull(fieldValue, "fieldValue cannot be null.");

        return with(resourceClass, ImmutableMap.<String, Object>builder().put(fieldName, fieldValue).build());
    }

    public static ResourceAlreadyExistsException with(final Class<?> resourceClass, final Map<String, Object> identifiers) {
        expectNotNull(resourceClass, "resourceClass cannot be null.");
        expectNotNull(identifiers, "identifiers cannot be null.");
        return new ResourceAlreadyExistsException(resourceClass, identifiers);
    }

    private static String constructMessage(final Class<?> resourceClass, final Map<String, Object> identifiers) {
        expectNotNull(resourceClass, "resourceClass cannot be null.");
        expectNotNull(identifiers, "identifiers cannot be null.");

        final String simpleResourceName = resourceClass.getSimpleName();

        final StringBuilder messageBuilder = new StringBuilder().append(simpleResourceName).append(" with identifiers [");

        final String identifierString = identifiers
            .entrySet()
            .stream()
            .map(stringObjectEntry -> String.format(
                "%s: %s",
                stringObjectEntry.getKey(),
                stringObjectEntry.getValue()
            )).collect(Collectors.joining(", "));

        messageBuilder.append(identifierString).append("] already exists.");

        return messageBuilder.toString();
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }

    public Map<String, Object> getIdentifiers() {
        return identifiers;
    }
}
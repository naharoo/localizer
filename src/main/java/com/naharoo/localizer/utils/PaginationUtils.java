package com.naharoo.localizer.utils;

import com.naharoo.localizer.domain.SortField;
import com.naharoo.localizer.domain.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public final class PaginationUtils {

    private PaginationUtils() {
        throw new IllegalAccessError();
    }

    public static PageRequest toPageRequest(
        final int from,
        final int size,
        final SortField sortField,
        final SortOrder sortOrder
    ) {
        if (from < 0 || size < 0) {
            throw new IllegalArgumentException("From/Size cannot be < 0");
        }

        int pageSize;
        int leftShift;
        for (pageSize = size; pageSize <= from + size; pageSize++) {
            for (leftShift = 0; leftShift <= pageSize - size; leftShift++) {
                if ((from - leftShift) % pageSize == 0) {
                    return PageRequest.of(
                        (from - leftShift) / pageSize,
                        pageSize,
                        Sort.Direction.fromString(sortOrder.name()),
                        sortField.getFieldName()
                    );
                }
            }
        }

        throw new IllegalStateException();
    }
}

package com.naharoo.localizer.e2e.resource;

import com.naharoo.localizer.e2e.locale.LocaleEndToEndTestHelper;

import java.util.Arrays;
import java.util.List;

public final class ResourceEndToEndTestHelper {

    private ResourceEndToEndTestHelper() {
        throw new IllegalAccessError();
    }

    public static List<ResourceTestData> getInitialData() {
        return Arrays.asList(
            new ResourceTestData(
                "1d1d37c1-f7c2-4202-bc7f-1d87f1edd91a",
                "document",
                LocaleEndToEndTestHelper.getInitialData().get(2),
                "Document",
                false
            ),
            new ResourceTestData(
                "6ed72ddc-6862-4d22-be8d-060713f953ef",
                "document",
                LocaleEndToEndTestHelper.getInitialData().get(0),
                "Փաստաթռւղթ",
                false
            ),
            new ResourceTestData(
                "b68bd7f5-9943-482e-af45-0abe318219c2",
                "tree",
                LocaleEndToEndTestHelper.getInitialData().get(1),
                "Дерево",
                false
            ),
            new ResourceTestData(
                "b0cd8a07-826d-40ce-9b34-e25a8a83a940",
                "tree",
                LocaleEndToEndTestHelper.getInitialData().get(2),
                "Tree",
                true
            )
        );
    }
}

package com.naharoo.localizer.e2e.locale;

import java.util.Arrays;
import java.util.List;

public final class LocaleEndToEndTestHelper {

    private LocaleEndToEndTestHelper() {
        throw new IllegalAccessError();
    }

    public static List<LocaleTestData> getInitialData() {
        return Arrays.asList(
            new LocaleTestData("6b7d0897-0273-480c-b984-0501240c22ab", "hy-AM", "Armenian"),
            new LocaleTestData("04285ec7-b2a2-4f5c-b2d2-5b6a2c0b1f81", "ru-RU", "Russian"),
            new LocaleTestData("7b54db99-0d29-4dac-8d92-742f935d2dca", "en-US", "English"),
            new LocaleTestData("fb73e83f-fd62-4230-89d9-d20c8ea00d39", "tr-TR", "Turkish")
        );
    }
}

package com.naharoo.localizer.e2e.locale;

import com.naharoo.localizer.e2e.AbstractEndToEndTest;
import com.naharoo.localizer.testutils.json.JsonWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchLocalesEndToEndTest extends AbstractEndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @Value("com/naharoo/localizer/e2e/json/locale/LocaleSearchRequest.json")
    JsonWrapper searchRequest;

    @Test
    @DisplayName("SearchLocales should return all not deleted Locales in DB when search request is empty json object")
    void search_200() throws Exception {
        // Given
        final String json = "{}";
        final List<LocaleTestData> expectedLocales = LocaleEndToEndTestHelper.getInitialData()
            .stream()
            .filter(localeTestData -> !localeTestData.isDeleted())
            .filter(localeTestData ->
                localeTestData.getKey().toLowerCase().contains(searchRequest.getString("query").toLowerCase()) ||
                    localeTestData.getName().toLowerCase().contains(searchRequest.getString("query").toLowerCase())
            )
            .sorted(Comparator.comparing(LocaleTestData::getKey).reversed())
            .collect(Collectors.toList());

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales/search")
                    .content(searchRequest.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.items", hasSize(expectedLocales.size())));

        for (int i = 0; i < expectedLocales.size(); i++) {
            final LocaleTestData locale = expectedLocales.get(i);
            result
                .andExpect(jsonPath(String.format("$.items[%s].id", i)).value(locale.getId()))
                .andExpect(jsonPath(String.format("$.items[%s].key", i)).value(locale.getKey()))
                .andExpect(jsonPath(String.format("$.items[%s].name", i)).value(locale.getName()));
        }
    }
}

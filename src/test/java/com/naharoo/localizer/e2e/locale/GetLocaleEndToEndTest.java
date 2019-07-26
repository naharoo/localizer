package com.naharoo.localizer.e2e.locale;

import com.naharoo.localizer.e2e.AbstractEndToEndTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetLocaleEndToEndTest extends AbstractEndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Locale should be successfully found (200) when Locale with specified id exists")
    void get_200() throws Exception {
        // Given
        final LocaleTestData testData = LocaleEndToEndTestHelper.getInitialData().get(0);
        final String id = testData.getId();
        final String key = testData.getKey();
        final String name = testData.getName();

        // When
        final ResultActions result = mockMvc
            .perform(
                get("/locales/id/" + id)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.key").value(key))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.created").isNotEmpty())
            .andExpect(jsonPath("$.updated").isNotEmpty())
            .andExpect(jsonPath("$.deleted").isEmpty());
    }

    @Test
    @DisplayName("GetLocale should respond with 404 Not Found when Locale with provided id is not found")
    void get_404() throws Exception {
        // Given
        final String id = UUID.randomUUID().toString();

        // When
        final ResultActions result = mockMvc
            .perform(
                get("/locales/id/" + id)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GetLocale should respond with 404 Not Found when Locale with provided id is deleted")
    void get_404_deleted() throws Exception {
        // Given
        final LocaleTestData testData = LocaleEndToEndTestHelper.getInitialData().get(3);
        final String id = testData.getId();

        // When
        final ResultActions result = mockMvc
            .perform(
                get("/locales/id/" + id)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isNotFound());
    }
}
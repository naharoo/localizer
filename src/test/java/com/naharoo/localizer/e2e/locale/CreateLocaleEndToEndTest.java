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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateLocaleEndToEndTest extends AbstractEndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @Value("com/naharoo/localizer/e2e/json/locale/LocaleCreationRequest.json")
    JsonWrapper localeCreationRequest;

    @Value("com/naharoo/localizer/e2e/json/locale/LocaleCreationRequestWithoutKey.json")
    JsonWrapper localeCreationRequestWithoutKey;

    @Value("com/naharoo/localizer/e2e/json/locale/LocaleCreationRequestWithoutName.json")
    JsonWrapper localeCreationRequestWithoutName;

    @Test
    @DisplayName("Locale should be successfully created (201) when input key is not already used")
    void create_201() throws Exception {
        // Given
        final String key = localeCreationRequest.getString("key");
        final String name = localeCreationRequest.getString("name");

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequest.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.key").value(key))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.created").exists())
            .andExpect(jsonPath("$.updated").exists());
    }

    @Test
    @DisplayName("Locale creation response should have 409 Conflict status when another locale with same key already exists")
    void create_409() throws Exception {
        // Given
        // LocaleCreationRequest json

        mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequest.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(status().isCreated());

        flushAndClear();

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequest.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result
            .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Locale creation response should have 400 Bad Request status when body is not provided")
    void create_400_noBody() throws Exception {
        // Given
        final String emptyBody = "";

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(emptyBody)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Locale creation response should have 400 Bad Request status when key is not provided")
    void create_400_noKey() throws Exception {
        // Given
        // LocaleCreationRequest without key

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequestWithoutKey.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Locale creation response should have 400 Bad Request status when name is not provided")
    void create_400_noName() throws Exception {
        // Given
        // LocaleCreationRequest without key

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequestWithoutName.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isBadRequest());
    }
}
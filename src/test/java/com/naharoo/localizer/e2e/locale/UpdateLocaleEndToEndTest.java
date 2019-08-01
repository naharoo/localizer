package com.naharoo.localizer.e2e.locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naharoo.localizer.e2e.AbstractEndToEndTest;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import com.naharoo.localizer.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.helper.LocaleTestHelper;
import com.naharoo.localizer.testutils.json.JsonWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateLocaleEndToEndTest extends AbstractEndToEndTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("com/naharoo/localizer/e2e/json/locale/LocaleCreationRequest.json")
    JsonWrapper localeCreationRequest;

    @Test
    @DisplayName("Locale should be successfully updated (200) when Locale with specified id exists")
    void update_200() throws Exception {
        // Given
        final MockHttpServletResponse response = mockMvc
            .perform(
                post("/locales")
                    .content(localeCreationRequest.getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(status().isCreated())
            .andReturn()
            .getResponse();

        final String contentAsString = response.getContentAsString();
        final LocaleDto createdLocale = objectMapper.readValue(contentAsString, LocaleDto.class);
        final String createdLocaleId = createdLocale.getId();

        flushAndClear();

        // Check locale is created
        mockMvc
            .perform(
                get("/locales/id/" + createdLocaleId)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(status().isOk());

        final LocaleModificationRequestDto modificationRequestDto = LocaleTestHelper.createLocaleModificationRequestDto(
            createdLocaleId,
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        );
        final String key = modificationRequestDto.getKey();
        final String name = modificationRequestDto.getName();

        // When
        mockMvc
            .perform(
                put("/locales")
                    .content(objectMapper.writeValueAsString(modificationRequestDto))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(status().isOk());

        flushAndClear();

        // Then
        mockMvc
            .perform(
                get("/locales/id/" + createdLocaleId)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(createdLocaleId))
            .andExpect(jsonPath("$.key").value(key))
            .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @DisplayName("UpdateLocale should respond with 404 Not Found when Locale with provided id is not found")
    void update_404() throws Exception {
        // Given
        final LocaleModificationRequestDto modificationRequestDto = LocaleTestHelper.createRandomLocaleModificationRequestDto();

        // When
        final ResultActions result = mockMvc
            .perform(
                put("/locales")
                    .content(objectMapper.writeValueAsString(modificationRequestDto))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            );

        // Then
        result.andExpect(status().isNotFound());
    }
}
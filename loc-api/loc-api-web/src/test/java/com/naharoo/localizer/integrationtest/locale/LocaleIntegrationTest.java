package com.naharoo.localizer.integrationtest.locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naharoo.localizer.api.facade.endpoint.locale.LocaleModificationRequestDto;
import com.naharoo.localizer.integrationtest.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class LocaleIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void create() throws Exception {
        // Given
        final String key = UUID.randomUUID().toString();
        final String name = UUID.randomUUID().toString();
        final LocaleModificationRequestDto creationRequest = new LocaleModificationRequestDto(key, name);

        // When
        final ResultActions result = mockMvc
            .perform(
                post("/locales")
                    .content(mapper.writeValueAsString(creationRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andDo(print());

        // Then
        result
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.key").value(key))
            .andExpect(jsonPath("$.name").value(name))
            .andExpect(jsonPath("$.created").exists())
            .andExpect(jsonPath("$.updated").exists());
    }
}

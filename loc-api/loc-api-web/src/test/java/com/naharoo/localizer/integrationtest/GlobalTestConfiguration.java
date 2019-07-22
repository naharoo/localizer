package com.naharoo.localizer.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@TestConfiguration
public class GlobalTestConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public MockMvc mockMvc(final WebApplicationContext context) {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json().build();
    }

    @Override
    protected void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        super.addDefaultHttpMessageConverters(converters);
    }
}

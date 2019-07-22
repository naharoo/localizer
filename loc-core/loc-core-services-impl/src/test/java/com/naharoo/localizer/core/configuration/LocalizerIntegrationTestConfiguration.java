package com.naharoo.localizer.core.configuration;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

@Configuration
@Profile("integrationtest")
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ComponentScan("com.naharoo.localizer")
@TestPropertySource(locations = "classpath:com/naharoo/localizer/application-integrationtest.properties")
public class LocalizerIntegrationTestConfiguration {
}

package com.naharoo.localizer.integrationtest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@TestConfiguration
@WebAppConfiguration
@Import(GlobalTestConfiguration.class)
@ActiveProfiles(value = {"test", "integrationtest"})
@EnabledIf(expression = "#{environment.acceptsProfiles('integrationtest')}", loadContext = true)
@ImportResource(locations = "classpath:com/naharoo/localizer/integrationtest/localizer-integrationtest-context.xml")
@Transactional
@ImportAutoConfiguration
public @interface IntegrationTest {
}

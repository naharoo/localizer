package com.naharoo.localizer.e2e;

import org.junit.jupiter.api.Tag;
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
@Tag("e2e")
@ActiveProfiles(value = {"test", "e2e"})
@EnabledIf(expression = "#{environment.acceptsProfiles('e2e')}", loadContext = true)
@ImportResource(locations = "classpath:com/naharoo/localizer/e2e/localizer-e2e-context.xml")
@Transactional
@ImportAutoConfiguration
public @interface E2ETest {
}

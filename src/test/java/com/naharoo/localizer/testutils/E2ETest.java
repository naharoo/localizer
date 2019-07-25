package com.naharoo.localizer.testutils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest
@Tag("e2e")
@ActiveProfiles(value = {"test", "e2e"})
@ImportResource(locations = "classpath:com/naharoo/localizer/e2e/localizer-e2e-test-context.xml")
@Transactional
public @interface E2ETest {
}
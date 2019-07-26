package com.naharoo.localizer.e2e;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest
@Tag("e2e")
@ActiveProfiles(value = {"test", "e2e"})
@ImportResource(locations = "classpath:com/naharoo/localizer/e2e/localizer-e2e-test-context.xml")
@Transactional
public abstract class AbstractEndToEndTest {

    @PersistenceContext
    EntityManager entityManager;

    protected void flush() {
        entityManager.flush();
    }

    protected void clear() {
        entityManager.clear();
    }

    protected void flushAndClear() {
        flush();
        clear();
    }
}

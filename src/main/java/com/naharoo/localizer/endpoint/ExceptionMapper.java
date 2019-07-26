package com.naharoo.localizer.endpoint;

import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<LocalizerApiError> handleResourceAlreadyExistsException(final ResourceAlreadyExistsException e) {
        logger.debug("Handling ResourceAlreadyExistsException...", e);

        final HttpStatus status = HttpStatus.CONFLICT;
        final LocalizerApiError localizerApiError = new LocalizerApiError(
            status.value(),
            Collections.singletonList(e.getMessage())
        );

        logger.info("Done handling ResourceAlreadyExistsException.", e);
        return new ResponseEntity<>(localizerApiError, status);
    }
}

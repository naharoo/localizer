package com.naharoo.localizer.endpoint;

import com.naharoo.localizer.exception.ResourceAlreadyExistsException;
import com.naharoo.localizer.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

@ControllerAdvice
public class ExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<LocalizerApiError> handleConstraintViolationException(final ConstraintViolationException e) {

        final HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new LocalizerApiError(
            status.value(),
            Collections.singletonList(e.getMessage())
        ), status);
    }

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<LocalizerApiError> handleResourceNotFoundException(final ResourceNotFoundException e) {
        logger.debug("Handling ResourceNotFoundException...", e);

        final HttpStatus status = HttpStatus.NOT_FOUND;
        final LocalizerApiError localizerApiError = new LocalizerApiError(
            status.value(),
            Collections.singletonList(e.getMessage())
        );

        logger.info("Done handling ResourceNotFoundException.", e);
        return new ResponseEntity<>(localizerApiError, status);
    }
}

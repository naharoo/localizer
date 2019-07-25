package com.naharoo.localizer.endpoint.locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(value = "/locales")
public interface LocalesEndpoint {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocaleDto create(@Valid @NotNull @RequestBody LocaleCreationRequestDto modificationRequestDto);
}
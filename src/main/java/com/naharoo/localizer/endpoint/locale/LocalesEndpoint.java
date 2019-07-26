package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequestMapping(value = "/locales")
public interface LocalesEndpoint {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocaleDto create(@Valid @NotNull @RequestBody(required = false) LocaleCreationRequestDto modificationRequestDto);

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocaleDto getById(@NotBlank @PathVariable(value = "id", required = false) final String id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    GenericListResponse<LocaleDto> search(@NotNull @Valid @RequestBody LocaleSearchRequestDto request);
}
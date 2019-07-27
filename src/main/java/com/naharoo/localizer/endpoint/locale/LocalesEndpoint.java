package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.endpoint.LocalizerApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(tags = "Locales", description = "Locales & relative endpoints")
@RequestMapping(value = "/locales")
public interface LocalesEndpoint {

    @ApiOperation(
        value = "Create a Locale",
        notes = "Creates a Locale and returns newly created Locale"
    )
    @ApiResponses({
        @ApiResponse(
            code = 201,
            message = "Successfully created a Locale",
            response = LocaleDto.class
        ),
        @ApiResponse(
            code = 409,
            message = "Another locale with same key already exists",
            response = LocalizerApiError.class
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiError.class
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocaleDto create(@Valid @NotNull @RequestBody(required = false) LocaleCreationRequestDto creationRequestDto);

    @ApiOperation(
        value = "Get a Locale by id",
        notes = "Gets a Locale using provided id",
        response = LocaleDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got a Locale",
            response = LocaleDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Locale has been found for provided id",
            response = LocalizerApiError.class
        )
    })
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocaleDto getById(@NotBlank @PathVariable(value = "id", required = false) final String id);

    @ApiOperation(
        value = "Searches for Locales list",
        notes = "Gets sorted and paginated Locales list"
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got sorted and paginated Locales list",
            response = LocaleDto.class,
            responseContainer = "List"
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiError.class
        )
    })
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    GenericListResponse<LocaleDto> search(@NotNull @Valid @RequestBody LocaleSearchRequestDto searchRequestDto);
}
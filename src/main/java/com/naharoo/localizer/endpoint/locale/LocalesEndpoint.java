package com.naharoo.localizer.endpoint.locale;

import com.naharoo.localizer.domain.GenericListResponse;
import com.naharoo.localizer.endpoint.LocalizerApiErrorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(tags = "Locales", description = "Locale Management & relative endpoints")
@RequestMapping(value = "/locales")
@RestController
@Validated
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
            response = LocalizerApiErrorDto.class
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiErrorDto.class
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
            response = LocalizerApiErrorDto.class
        )
    })
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocaleDto getById(@NotBlank @PathVariable(value = "id", required = false) String id);

    @ApiOperation(
        value = "Get a Locale by key",
        notes = "Gets a Locale using provided key",
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
            message = "No Locale has been found for provided key",
            response = LocalizerApiErrorDto.class
        )
    })
    @GetMapping("/key/{key}")
    @ResponseStatus(HttpStatus.OK)
    LocaleDto getByKey(@NotBlank @PathVariable(value = "key", required = false) String key);

    @ApiOperation(
        value = "Searches for Locales list",
        notes = "Gets filtered, sorted and paginated Locales list",
        response = LocaleDto.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got filtered, sorted and paginated Locales list",
            response = LocaleDto.class,
            responseContainer = "List"
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiErrorDto.class
        )
    })
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    GenericListResponse<LocaleDto> search(@NotNull @Valid @RequestBody LocaleSearchRequestDto searchRequestDto);

    @ApiOperation(
        value = "Delete a Locale by id",
        notes = "Deletes a Locale using provided id",
        response = LocaleDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully deleted a Locale",
            response = LocaleDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Locale has been found for provided id",
            response = LocalizerApiErrorDto.class
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiErrorDto.class
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocaleDto delete(@NotBlank @PathVariable(value = "id", required = false) String id);

    @ApiOperation(
        value = "Update a Locale",
        notes = "Updates a Locale identified with provided id by provided data",
        response = LocaleDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully updated a Locale",
            response = LocaleDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Locale has been found for provided id",
            response = LocalizerApiErrorDto.class
        ),
        @ApiResponse(
            code = 400,
            message = "Request data violates some constraints",
            response = LocalizerApiErrorDto.class
        )
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    LocaleDto update(@NotNull @Valid @RequestBody LocaleModificationRequestDto modificationRequestDto);
}
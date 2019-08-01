package com.naharoo.localizer.endpoint.resource;

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

@Api(tags = "Resources", description = "Resource Management & relative endpoints")
@RequestMapping(value = "/resources")
@RestController
@Validated
public interface ResourcesEndpoint {

    @ApiOperation(
        value = "Create a Resource",
        notes = "Creates and returns newly created Resource"
    )
    @ApiResponses({
        @ApiResponse(
            code = 201,
            message = "Successfully created a Resource",
            response = ResourceDto.class
        ),
        @ApiResponse(
            code = 409,
            message = "Another Resource with same key and locale already exists",
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
    ResourceDto create(@Valid @NotNull @RequestBody(required = false) ResourceCreationRequestDto creationRequestDto);

    @ApiOperation(
        value = "Get a Resource by id",
        notes = "Gets a Resource using provided id",
        response = ResourceDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got a Resource",
            response = ResourceDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Resource has been found for provided id",
            response = LocalizerApiErrorDto.class
        )
    })
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResourceDto getById(@NotBlank @PathVariable(value = "id", required = false) String id);

    @ApiOperation(
        value = "Get a Resource by key and Locale key",
        notes = "Gets a Resource using provided Resource key and Locale key",
        response = ResourceDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got a Resource",
            response = ResourceDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Locale/Resource has been found for provided key / key combination",
            response = LocalizerApiErrorDto.class
        )
    })
    @GetMapping("/key/{key}/locale/{localeKey}")
    @ResponseStatus(HttpStatus.OK)
    ResourceDto getByKeyAndLocaleKey(
        @NotBlank @PathVariable(value = "key", required = false) String key,
        @NotBlank @PathVariable(value = "localeKey", required = false) String localeKey
    );

    @ApiOperation(
        value = "Searches for Resources list",
        notes = "Gets filtered, sorted and paginated Resources list",
        response = ResourceDto.class,
        responseContainer = "List"
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully got filtered, sorted and paginated Resources list",
            response = ResourceDto.class,
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
    GenericListResponse<ResourceDto> search(@NotNull @Valid @RequestBody ResourceSearchRequestDto searchRequestDto);

    @ApiOperation(
        value = "Delete a Resource by id",
        notes = "Deletes a Resource using provided id",
        response = ResourceDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully deleted a Resource",
            response = ResourceDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Resource has been found for provided id",
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
    ResourceDto delete(@NotBlank @PathVariable(value = "id", required = false) String id);

    @ApiOperation(
        value = "Update a Resource",
        notes = "Updates a Resource identified with provided id by provided data",
        response = ResourceDto.class
    )
    @ApiResponses({
        @ApiResponse(
            code = 200,
            message = "Successfully updated a Resource",
            response = ResourceDto.class
        ),
        @ApiResponse(
            code = 404,
            message = "No Resource has been found for provided id",
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
    ResourceDto update(@NotNull @Valid @RequestBody ResourceModificationRequestDto modificationRequestDto);
}

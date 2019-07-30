package com.naharoo.localizer.endpoint.resource;

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

@Api(tags = "Resources", description = "Resources & relative endpoints")
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
}

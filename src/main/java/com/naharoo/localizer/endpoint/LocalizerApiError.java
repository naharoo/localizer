package com.naharoo.localizer.endpoint;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Class representing Localizer Api Errors and containing their data")
public final class LocalizerApiError {

    @ApiModelProperty(
        notes = "HTTP Status of response.",
        example = "400",
        required = true,
        position = 0
    )
    private final int status;

    @ApiModelProperty(
        notes = "Response Messages.",
        example = "['getById.id': 'must not be blank']",
        required = true,
        position = 1
    )
    private final List<String> messages;

    @JsonCreator
    public LocalizerApiError(
        @JsonProperty("status") final int status,
        @JsonProperty("messages") final List<String> messages
    ) {
        this.status = status;
        this.messages = messages;
    }
}

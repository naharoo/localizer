package com.naharoo.localizer.endpoint.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.naharoo.localizer.endpoint.locale.LocaleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "Class representing a Resource that is localized in the application")
public class ResourceDto {

    @ApiModelProperty(
        notes = "Unique identifier of the resource. No two resources can have the same id.",
        example = "cbd5cf7a-b8cc-41ed-9998-c12111df81e3",
        required = true,
        position = 0
    )
    private final String id;

    @ApiModelProperty(
        notes = "Key of the Resource. Together with Locale is composite unique identifier of Resource. No two resources can have the same key + locale pair.",
        example = "document",
        required = true,
        position = 1
    )
    private final String key;

    @ApiModelProperty(
        notes = "Locale of Resource. Together with Key is composite unique identifier of Resource. No two resources can have the same key + locale pair.",
        example = "{'key':'en-US'}",
        required = true,
        position = 2
    )
    private final LocaleDto locale;

    @ApiModelProperty(
        notes = "Localized value of the Resource.",
        example = "Document",
        required = true,
        position = 3
    )
    private final String value;

    @ApiModelProperty(
        notes = "Creation date of the Resource.",
        example = "2019-07-26T14:21:26.299831",
        required = true,
        position = 4
    )
    private final LocalDateTime created;

    @ApiModelProperty(
        notes = "Last modification date of the Resource.",
        example = "2019-07-26T14:21:26.299831",
        required = true,
        position = 5
    )
    private final LocalDateTime updated;

    @ApiModelProperty(
        notes = "Deletion date of the Resource.",
        example = "2019-07-26T14:21:26.299831",
        position = 6
    )
    private final LocalDateTime deleted;

    @JsonCreator
    public ResourceDto(
        @JsonProperty("id") final String id,
        @JsonProperty("key") final String key,
        @JsonProperty("locale") final LocaleDto locale,
        @JsonProperty("value") final String value,
        @JsonProperty("created") final LocalDateTime created,
        @JsonProperty("updated") final LocalDateTime updated,
        @JsonProperty("deleted") final LocalDateTime deleted
    ) {
        this.id = id;
        this.key = key;
        this.locale = locale;
        this.value = value;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
    }
}
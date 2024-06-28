package dev.roadfind.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DocumentDto(
        @JsonProperty("address_name")
        String addressName,
        @JsonProperty("y")
        Double latitude,
        @JsonProperty("x")
        Double longitude
) {
}

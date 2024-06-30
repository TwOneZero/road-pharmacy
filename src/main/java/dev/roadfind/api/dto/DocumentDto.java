package dev.roadfind.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DocumentDto(
        @JsonProperty("place_name")
        String placeName,
        @JsonProperty("address_name")
        String addressName,
        @JsonProperty("y")
        Double latitude,
        @JsonProperty("x")
        Double longitude,
        @JsonProperty("distance")
        Double distance
) {
}

package dev.roadfind.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KakaoApiResponseDto(

        @JsonProperty("meta")
        MetaDto metaDto,
        @JsonProperty("documents")
        List<DocumentDto> documentDtoList
) {
}

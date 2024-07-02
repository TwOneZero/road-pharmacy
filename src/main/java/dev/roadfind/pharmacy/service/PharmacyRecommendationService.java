package dev.roadfind.pharmacy.service;

import dev.roadfind.api.dto.KakaoApiResponseDto;
import dev.roadfind.api.service.KakaoAddressSearchService;
import dev.roadfind.direction.dto.OutputDto;
import dev.roadfind.direction.entity.Direction;
import dev.roadfind.direction.service.Base62Service;
import dev.roadfind.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    @Value("%{pharmacy.recommendation.base.url}")
    private static String baseUrl;




    public List<OutputDto> recommendPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto  = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.documentDtoList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address : {} ", address);
            return Collections.emptyList();
        }

        var documentDto = kakaoApiResponseDto.documentDtoList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        //추천된
        var directionEntityList = directionService.saveAll(directionList);

        return directionEntityList.stream()
                .map(this::mapToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto mapToOutputDto(Direction direction) {

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .distance(String.format("%.2f km",direction.getDistance()))
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .build();
    }

}


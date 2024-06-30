package dev.roadfind.pharmacy.service;

import dev.roadfind.api.dto.KakaoApiResponseDto;
import dev.roadfind.api.service.KakaoAddressSearchService;
import dev.roadfind.direction.entity.Direction;
import dev.roadfind.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto  = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.documentDtoList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address : {} ", address);
            return;
        }

        var documentDto = kakaoApiResponseDto.documentDtoList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        //추천된
        directionService.saveAll(directionList);
    }

}


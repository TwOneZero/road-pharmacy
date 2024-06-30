package dev.roadfind.direction.service;


import dev.roadfind.api.dto.DocumentDto;
import dev.roadfind.api.service.KakaoCategorySearchService;
import dev.roadfind.direction.entity.Direction;
import dev.roadfind.direction.repository.DirectionRepository;
import dev.roadfind.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {
    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;

    private final PharmacySearchService pharmacySearchService;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final DirectionRepository directionRepository;


    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {
        // 클라이언트의 위도 경도 정보를 바탕으로 가까운 약국 3곳 추천
        // 1. 약국 데이터 조회
        // 2. 거리계산 알고리즘을 통해 클라이언트와 약국 거리 계산 -> sort

        if (Objects.isNull(documentDto)) {
            return Collections.emptyList();
        }

        return pharmacySearchService.searchPharmacyDtoList().stream()
                .map(pharmacyDto -> Direction.builder()
                        .inputAddress(documentDto.addressName())
                        .inputLatitude(documentDto.latitude())
                        .inputLongitude(documentDto.longitude())
                        .targetPharmacyName(pharmacyDto.pharmacyName())
                        .targetAddress(pharmacyDto.pharmacyAddress())
                        .targetLatitude(pharmacyDto.latitude())
                        .targetLongitude(pharmacyDto.longitude())
                        .distance(calculateDistance(
                                documentDto.latitude(), documentDto.longitude(),
                                pharmacyDto.latitude(), pharmacyDto.longitude()
                        )).build()
                ).filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<Direction> buildDirectionListByCategoryApi(DocumentDto documentDto) {
        if (Objects.isNull(documentDto)) {
            return Collections.emptyList();
        }

        return kakaoCategorySearchService.requestCategorySearch(documentDto.latitude(), documentDto.longitude(), RADIUS_KM)
                .documentDtoList()
                .stream()
                .map(resultDocumentDto -> Direction.builder()
                        .inputAddress(documentDto.addressName())
                        .inputLatitude(documentDto.latitude())
                        .inputLongitude(documentDto.longitude())
                        .targetPharmacyName(resultDocumentDto.placeName())
                        .targetAddress(resultDocumentDto.addressName())
                        .targetLatitude(resultDocumentDto.latitude())
                        .targetLongitude(resultDocumentDto.longitude())
                        .distance(resultDocumentDto.distance() * 0.001)
                        .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());


    }


    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}

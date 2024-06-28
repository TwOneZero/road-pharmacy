package dev.roadfind.direction.service;


import dev.roadfind.api.dto.DocumentDto;
import dev.roadfind.direction.entity.Direction;
import dev.roadfind.pharmacy.dto.PharmacyDto;
import dev.roadfind.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {
    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;

    private final PharmacySearchService pharmacySearchService;

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

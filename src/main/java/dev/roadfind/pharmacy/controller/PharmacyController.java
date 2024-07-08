package dev.roadfind.pharmacy.controller;


import dev.roadfind.pharmacy.cache.PharmacyRedisTemplateService;
import dev.roadfind.pharmacy.dto.PharmacyDto;
import dev.roadfind.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;
    private final PharmacyRepositoryService pharmacyRepositoryService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {
        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.getAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build())
                .toList();

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }

}

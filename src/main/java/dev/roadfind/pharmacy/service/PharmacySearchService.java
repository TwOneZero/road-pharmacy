package dev.roadfind.pharmacy.service;


import dev.roadfind.pharmacy.cache.PharmacyRedisTemplateService;
import dev.roadfind.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    public List<PharmacyDto> searchPharmacyDtoList() {
        //redis 캐싱
        List<PharmacyDto> pharmacyDto = pharmacyRedisTemplateService.findAll();

        //failover -> db 에서 조회
        if (!pharmacyDto.isEmpty()) {
            return pharmacyDto;
        }
        return pharmacyRepositoryService.getAll().stream()
                .map(PharmacyDto::from)
                .toList();

    }

}

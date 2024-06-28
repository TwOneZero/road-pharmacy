package dev.roadfind.pharmacy.service;


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

    public List<PharmacyDto> searchPharmacyDtoList() {
        //redis 캐싱

        //db 조회
        return pharmacyRepositoryService.getAll().stream()
                .map(PharmacyDto::from)
                .toList();
    }

}

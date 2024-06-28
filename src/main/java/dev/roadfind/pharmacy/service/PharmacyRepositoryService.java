package dev.roadfind.pharmacy.service;


import dev.roadfind.pharmacy.entity.Pharmacy;
import dev.roadfind.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        var pharmacy = pharmacyRepository.findById(id)
                .orElse(null);
        if (!Objects.isNull(pharmacy)) {
            pharmacy.changePharmacyAddress(address);
        }
        log.error("[PharmacyRepositoryService updateAddress] not found id : {}",id);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
    }



}

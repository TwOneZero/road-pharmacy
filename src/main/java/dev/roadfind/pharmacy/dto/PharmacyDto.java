package dev.roadfind.pharmacy.dto;

import dev.roadfind.pharmacy.entity.Pharmacy;
import lombok.Builder;

@Builder
public record PharmacyDto(
        Long id,
        String pharmacyName,
        String pharmacyAddress,
        Double latitude,
        Double longitude
) {

    public static PharmacyDto from(Pharmacy entity) {
        return PharmacyDto.builder()
                .id(entity.getId())
                .pharmacyName(entity.getPharmacyName())
                .pharmacyAddress(entity.getPharmacyAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}

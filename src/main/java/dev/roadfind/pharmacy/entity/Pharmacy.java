package dev.roadfind.pharmacy.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity(name = "pharmacy")
public class Pharmacy extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pharmacyName;
    private String pharmacyAddress;
    private Double latitude;
    private Double longitude;


    protected Pharmacy(){}

    @Builder
    public Pharmacy(Long id, String pharmacyName, String pharmacyAddress, Double latitude, Double longitude) {
        this.id = id;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void changePharmacyAddress(String address) {
        this.pharmacyAddress = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(id, pharmacy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


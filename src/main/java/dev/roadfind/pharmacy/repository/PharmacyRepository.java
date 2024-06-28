package dev.roadfind.pharmacy.repository;

import dev.roadfind.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy ,Long> {
}

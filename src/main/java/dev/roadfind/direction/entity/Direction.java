package dev.roadfind.direction.entity;

import dev.roadfind.pharmacy.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity(name = "direction")
public class Direction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 클라이언트 정보
    private String inputAddress;
    private Double inputLatitude;
    private Double inputLongitude;
    // 약국 정보
    private String targetPharmacyName;
    private String targetAddress;
    private Double targetLatitude;
    private Double targetLongitude;
    private Double distance;

    protected Direction(){}

    @Builder
    public Direction(String inputAddress, Double inputLatitude, Double inputLongitude, String targetPharmacyName, String targetAddress, Double targetLatitude, Double targetLongitude, Double distance) {
        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetPharmacyName = targetPharmacyName;
        this.targetAddress = targetAddress;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return Objects.equals(id, direction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

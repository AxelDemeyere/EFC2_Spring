package org.example.environement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "La distance est obligatoire")
    @DecimalMin(value = "0.0", message = "La distance doit être positive ou nulle")
    @DecimalMax(value = "50000.0", message = "La distance ne peut pas dépasser 50 000 km")
    private Double distanceKm;

    @Column(nullable = false)
    @NotNull(message = "Le mode de transport est obligatoire")
    private TravelMode mode;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "L'émission CO2 doit être positive ou nulle")
    private Double estimatedCo2Kg;

    @ManyToOne
    @NotNull(message = "L'observation associée est obligatoire")
    private Observation observation;

    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder()
                .id(this.id)
                .distanceKm(this.distanceKm)
                .mode(String.valueOf(this.mode))
                .estimatedCo2Kg(this.calculateCO2())
                .build();
    }


    public Double calculateCO2() {
        if (this.mode != null && this.distanceKm != null) {
            double emissionFactor;
            switch (this.mode) {
                case CAR -> emissionFactor = 0.22;
                case BUS -> emissionFactor = 0.11;
                case TRAIN -> emissionFactor = 0.03;
                case PLANE -> emissionFactor = 0.259;
                case BIKE, WALKING -> emissionFactor = 0.0;
                default -> throw new IllegalArgumentException("Unknown travel mode: " + this.mode);
            }
            this.estimatedCo2Kg = this.distanceKm * emissionFactor;
        } else {
            this.estimatedCo2Kg = 0.0;
        }
        return this.estimatedCo2Kg;
    }
}

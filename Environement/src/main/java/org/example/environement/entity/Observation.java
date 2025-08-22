package org.example.environement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.observation.ObservationDtoResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom de l'observateur est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String observerName;

    @Column(nullable = false)
    @NotBlank(message = "Le lieu est obligatoire")
    @Size(max = 200, message = "Le lieu ne peut pas dépasser 200 caractères")
    private String location;

    @Column(nullable = false)
    @NotNull(message = "La latitude est obligatoire")
    @DecimalMin(value = "-90.0", message = "La latitude doit être comprise entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être comprise entre -90 et 90")
    private Double latitude;

    @Column(nullable = false)
    @NotNull(message = "La longitude est obligatoire")
    @DecimalMin(value = "-180.0", message = "La longitude doit être comprise entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être comprise entre -180 et 180")
    private Double longitude;

    @Column(nullable = false)
    @NotNull(message = "La date d'observation est obligatoire")
    @PastOrPresent(message = "La date d'observation ne peut pas être dans le futur")
    private LocalDate observationDate;

    @Size(max = 500, message = "Le commentaire ne peut pas dépasser 500 caractères")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    @NotNull(message = "L'espèce est obligatoire")
    private Specie specie;

    @OneToMany(mappedBy = "observation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Travellog> travellogs;


    public ObservationDtoResponse entityToDto (){
        return ObservationDtoResponse.builder()
                .id(this.getId())
                .observerName(this.getObserverName())
                .location(this.getLocation())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .observationDate(this.getObservationDate())
                .comment(this.getComment())
                .specie(this.getSpecie().entityToDto())
                .travellogs(this.getTravellogs().stream().map(Travellog::entityToDto).collect(Collectors.toList()))
                .build();
    }
}

package org.example.environement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.specie.SpecieDtoResponse;
import org.example.environement.entity.enums.Category;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Le nom commun est obligatoire")
    private String commonName;

    @Column(nullable = false)
    @NotBlank(message = "Le nom scientifique est obligatoire")
    private String scientificName;

    @Column(nullable = false)
    @NotNull(message = "La catégorie est obligatoire")
    private Category category;


    public SpecieDtoResponse entityToDto (){
        return SpecieDtoResponse.builder()
                .id(this.getId())
                .category(this.getCategory().toString())
                .scientificName(this.getScientificName())
                .commonName(this.getCommonName())
                .build();
    }

}

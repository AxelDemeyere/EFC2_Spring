package org.example.environement.dto.specie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SpecieDtoResponse {
    private long id;
    private String commonName;
    private String scientificName;
    private String category;
}

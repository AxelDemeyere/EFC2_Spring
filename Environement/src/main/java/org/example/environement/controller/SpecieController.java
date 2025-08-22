package org.example.environement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.environement.dto.specie.SpecieDtoReceive;
import org.example.environement.dto.specie.SpecieDtoResponse;
import org.example.environement.service.SpecieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/species")
@Tag(name = "Espèces", description = "Gestion des espèces")
public class SpecieController {

    private final SpecieService specieService;

    public SpecieController(SpecieService specieService) {
        this.specieService = specieService;
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les espèces", description = "Cette méthode permet de récupérer la liste de toutes les espèces.")
    public ResponseEntity<List<SpecieDtoResponse>> getSpecies(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(specieService.get(pageSize, pageNumber));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une espèce par ID", description = "Cette méthode permet de récupérer une espèce spécifique en utilisant son ID.")
    public ResponseEntity<SpecieDtoResponse> getSpecieById(@PathVariable long id) {
        return ResponseEntity.ok(specieService.get(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle espèce", description = "Cette méthode permet de créer une nouvelle espèce en fournissant les détails nécessaires.")
    public ResponseEntity<SpecieDtoResponse> createSpecie(@Valid @RequestBody SpecieDtoReceive specieDtoReceive) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specieService.create(specieDtoReceive));
    }
}

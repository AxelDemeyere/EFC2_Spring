package org.example.environement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.environement.dto.observation.ObservationDtoReceive;
import org.example.environement.dto.observation.ObservationDtoResponse;
import org.example.environement.service.ObservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/observations")
@Tag(name = "Observations", description = "Gestion des observations")
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les observations", description = "Cette méthode permet de récupérer la liste de toutes les observations.")
    public ResponseEntity<List<ObservationDtoResponse>> getAllObservations(
            @RequestParam(defaultValue = "2") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(observationService.get(pageSize, pageNumber));
    }


    @PostMapping
    @Operation(summary = "Créer une nouvelle observation", description = "Cette méthode permet de créer une nouvelle observation en fournissant les détails nécessaires.")
    public ResponseEntity<ObservationDtoResponse> createObservation(@Valid @RequestBody ObservationDtoReceive observationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(observationService.create(observationDto));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une observation par ID", description = "Cette méthode permet de récupérer une observation spécifique en utilisant son ID.")
    public ResponseEntity<ObservationDtoResponse> getObservationById(@PathVariable long id) {
        return ResponseEntity.ok(observationService.get(id));
    }


    @GetMapping("/by-location")
    @Operation(summary = "Récupérer les observations par lieu", description = "Cette méthode permet de récupérer les observations effectuées à un endroit spécifique.")
    public ResponseEntity<List<ObservationDtoResponse>> getObservationsByLocation(@RequestParam String location) {
        return ResponseEntity.ok(observationService.getByLocation(location));
    }


    @GetMapping("/by-species/{speciesId}")
    @Operation(summary = "Récupérer les observations par espèce", description = "Cette méthode permet de récupérer les observations associées à une espèce spécifique en utilisant l'ID de l'espèce.")
    public ResponseEntity<List<ObservationDtoResponse>> getObservationsBySpecies(@PathVariable long speciesId) {
        return ResponseEntity.ok(observationService.getBySpecie(speciesId));
    }
}

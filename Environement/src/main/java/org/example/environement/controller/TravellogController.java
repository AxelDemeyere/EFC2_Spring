package org.example.environement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.service.TravellogsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/travel-logs")
@Tag(name = "Journal de voyage", description = "Gestion des journaux de voyage")
public class TravellogController {

    private final TravellogsService travellogsService;

    public TravellogController(TravellogsService travellogsService) {
        this.travellogsService = travellogsService;
    }


    @PostMapping
    @Operation(summary = "Créer un nouveau journal de voyage", description = "Cette méthode permet de créer un nouveau journal de voyage en fournissant les détails nécessaires.")
    public ResponseEntity<TravellogDtoResponse> createTravelLog(@Valid @RequestBody TravellogDtoReceive travellogDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(travellogsService.create(travellogDto));
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les journaux de voyage", description = "Cette méthode permet de récupérer la liste de tous les journaux de voyage.")
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs() {
        return ResponseEntity.ok(travellogsService.get(10));
    }

    @GetMapping("/stats/{idObservation}")
    @Operation(summary = "Récupérer les statistiques d'un journal de voyage par ID d'observation", description = "Cette méthode permet de récupérer les statistiques associées à un journal de voyage spécifique en utilisant l'ID de l'observation.")
    public ResponseEntity<TravellogDtoStat> getStatFromObservation(@PathVariable long idObservation) {
        return ResponseEntity.ok(travellogsService.getStat(idObservation));
    }

    @GetMapping("/user/{name}")
    @Operation(summary = "Récupérer les statistiques des journaux de voyage d'un utilisateur pour le dernier mois", description = "Cette méthode permet de récupérer les statistiques agrégées des journaux de voyage d'un utilisateur spécifique pour le dernier mois.")
    public ResponseEntity<Map<String, TravellogDtoStat>> getTravelStatForUserOnLastMonth(@PathVariable String name) {
        return ResponseEntity.ok(travellogsService.getStatForUserLastMonth(name));
    }



}

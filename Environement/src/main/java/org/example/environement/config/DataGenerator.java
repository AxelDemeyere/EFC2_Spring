package org.example.environement.config;

import jakarta.annotation.PostConstruct;
import org.example.environement.entity.Observation;
import org.example.environement.entity.Specie;
import org.example.environement.entity.Travellog;
import org.example.environement.entity.enums.Category;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.SpecieRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataGenerator {

    private final TravellogRepository travellogRepository;
    private final SpecieRepository specieRepository;
    private final ObservationRepository observationRepository;

    public DataGenerator(TravellogRepository travellogRepository, SpecieRepository specieRepository, ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.specieRepository = specieRepository;
        this.observationRepository = observationRepository;
    }

    @PostConstruct
    public void generateData() {

        if (specieRepository.count() > 0) {
            return;
        }

        Specie specie1 = Specie.builder()
                .commonName("Mésange bleue")
                .scientificName("Cyanistes caeruleus")
                .category(Category.BIRD)
                .build();

        Specie specie2 = Specie.builder()
                .commonName("Écureuil roux")
                .scientificName("Sciurus vulgaris")
                .category(Category.MAMMAL)
                .build();

        Specie specie3 = Specie.builder()
                .commonName("Libellule déprimée")
                .scientificName("Libellula depressa")
                .category(Category.INSECT)
                .build();

        specieRepository.save(specie1);
        specieRepository.save(specie2);
        specieRepository.save(specie3);

        Observation obs1 = Observation.builder()
                .observerName("Jean Dupont")
                .location("Paris")
                .latitude(48.8566)
                .longitude(2.3522)
                .observationDate(LocalDate.now().minusDays(5))
                .comment("Observation matinale dans le parc")
                .specie(specie1)
                .build();

        Observation obs2 = Observation.builder()
                .observerName("Marie Martin")
                .location("Lyon")
                .latitude(45.7640)
                .longitude(4.8357)
                .observationDate(LocalDate.now().minusDays(15))
                .comment("Famille d'écureuils dans la forêt")
                .specie(specie2)
                .build();

        Observation obs3 = Observation.builder()
                .observerName("Pierre Durand")
                .location("Marseille")
                .latitude(43.2965)
                .longitude(5.3698)
                .observationDate(LocalDate.now().minusDays(30))
                .comment("Vol au-dessus du lac")
                .specie(specie3)
                .build();

        observationRepository.save(obs1);
        observationRepository.save(obs2);
        observationRepository.save(obs3);

        Travellog travel1 = Travellog.builder()
                .distanceKm(15.5)
                .mode(TravelMode.CAR)
                .observation(obs1)
                .estimatedCo2Kg(15.5 * 0.22)
                .build();

        Travellog travel2 = Travellog.builder()
                .distanceKm(8.0)
                .mode(TravelMode.BIKE)
                .observation(obs1)
                .estimatedCo2Kg(0.0)
                .build();

        Travellog travel3 = Travellog.builder()
                .distanceKm(120.0)
                .mode(TravelMode.TRAIN)
                .observation(obs2)
                .estimatedCo2Kg(120.0 * 0.03)
                .build();

        Travellog travel4 = Travellog.builder()
                .distanceKm(25.0)
                .mode(TravelMode.WALKING)
                .observation(obs3)
                .estimatedCo2Kg(0.0)
                .build();

        Travellog travel5 = Travellog.builder()
                .distanceKm(50.0)
                .mode(TravelMode.PLANE)
                .observation(obs2)
                .estimatedCo2Kg(50.0 * 0.259)
                .build();

        Travellog travel6 = Travellog.builder()
                .distanceKm(12.0)
                .mode(TravelMode.BUS)
                .observation(obs1)
                .estimatedCo2Kg(12.0 * 0.11)
                .build();

        travellogRepository.save(travel1);
        travellogRepository.save(travel2);
        travellogRepository.save(travel3);
        travellogRepository.save(travel4);
        travellogRepository.save(travel5);
        travellogRepository.save(travel6);
    }
}
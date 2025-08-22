package org.example.environement.service;

import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.entity.Travellog;
import org.example.environement.repository.TravellogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TravellogsService {

    private final TravellogRepository travellogRepository;

    public TravellogsService(TravellogRepository travellogRepository) {
        this.travellogRepository = travellogRepository;
    }

    public List<TravellogDtoResponse> get(int limit){
        return travellogRepository.findAll().stream().map(Travellog::entityToDto).toList();
    }

    public TravellogDtoStat getStat(long id) {
        Travellog travellog = travellogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travellog not found"));
        return TravellogDtoStat.fromEntity(travellog);
    }


    public Map<String, TravellogDtoStat> getStatForUserLastMonth(String name) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Travellog> userTravellogs = travellogRepository.findTravellogByUserForLastMonth(name, oneMonthAgo);

        Map<String, TravellogDtoStat> statsMap = new HashMap<>();

        for (Travellog travellog : userTravellogs) {
            String key = "Observation " + travellog.getObservation().getId();

            TravellogDtoStat existingStats = statsMap.getOrDefault(key, new TravellogDtoStat());


            existingStats.addTotalDistanceKm(travellog.getDistanceKm());
            existingStats.addTotalEmissionsKg(travellog.getEstimatedCo2Kg());
            existingStats.addMode(travellog.getMode().toString(), travellog.getDistanceKm());

            statsMap.put(key, existingStats);
        }

        return statsMap;
    }

    public TravellogDtoResponse create(TravellogDtoReceive travellogDto) {
        Travellog travellog = travellogDto.dtoToEntity();
        Travellog savedTravellog = travellogRepository.save(travellog);
        return savedTravellog.entityToDto();
    }
}

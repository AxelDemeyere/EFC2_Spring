package org.example.environement.dto.travellogs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.environement.entity.Travellog;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Data
@Builder
public class TravellogDtoStat {
    private double totalDistanceKm;
    private double totalEmissionsKg;
    private Map<String,Double> byMode;

    public TravellogDtoStat() {
        totalDistanceKm = 0;
        totalEmissionsKg = 0;
        byMode = new HashMap<>();
    }

    public static TravellogDtoStat fromEntity(Travellog travellog) {
        TravellogDtoStat stat = new TravellogDtoStat();

        double distance = travellog.getDistanceKm() != null ? travellog.getDistanceKm() : 0.0;

        Double co2 = travellog.getEstimatedCo2Kg();
        if (co2 == null) {
            co2 = travellog.calculateCO2();
        }
        double emissions = co2 != null ? co2 : 0.0;

        stat.addTotalDistanceKm(distance);
        stat.addTotalEmissionsKg(emissions);
        stat.addMode(travellog.getMode().toString(), distance);

        return stat;
    }

    public void addTotalDistanceKm(double totalDistanceKm) {
        this.totalDistanceKm += totalDistanceKm;
    }

    public void addTotalEmissionsKg(double totalEmissionsKg) {
        this.totalEmissionsKg += totalEmissionsKg;
    }

    public void addMode(String mode,double distance) {
        this.byMode.put(mode,distance);
    }

}
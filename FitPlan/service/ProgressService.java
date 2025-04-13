package service;

import model.Measurement;
import model.WeightEntry;
import repository.ProgressRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
//Provides business logic related to tracking progress (weight, measurements).//
public class ProgressService {
    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public void addWeightEntry(String username, double weightKg, LocalDate date) {
        if (weightKg <= 0) {
            System.err.println("[ProgressService] Error: Weight must be positive.");
            return;
        }
        WeightEntry entry = new WeightEntry(date, weightKg);
        progressRepository.addWeightEntry(username, entry);
         System.out.println("[ProgressService] Weight entry added for " + username + " on " + date);
    }

    public List<WeightEntry> getWeightHistory(String username) {
        return progressRepository.getWeightHistory(username);
    }

    public void addMeasurement(String username, Map<String, Double> measurements, LocalDate date) {
        if (measurements == null || measurements.isEmpty()) {
            System.err.println("[ProgressService] Error: No measurements provided.");
            return;
        }
        // Optional: Add validation for measurement values (e.g., positive)
         for (double value : measurements.values()) {
            if (value <= 0) {
                System.err.println("[ProgressService] Error: All measurements must be positive.");
                return;
            }
         }

        Measurement entry = new Measurement(date, measurements);
        progressRepository.addMeasurement(username, entry);
         System.out.println("[ProgressService] Measurement entry added for " + username + " on " + date);
    }

    public List<Measurement> getMeasurementHistory(String username) {
        return progressRepository.getMeasurementHistory(username);
    }

}
package FitPlan.repository;

import FitPlan.model.Measurement;
import FitPlan.model.WeightEntry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ProgressRepository {
    // Map<Username, List<WeightEntry>>
    private final Map<String, List<WeightEntry>> weightHistory = new ConcurrentHashMap<>();
    // Map<Username, List<Measurement>>
    private final Map<String, List<Measurement>> measurementHistory = new ConcurrentHashMap<>();

    public void addWeightEntry(String username, WeightEntry entry) {
        weightHistory.computeIfAbsent(username, k -> new ArrayList<>()).add(entry);
         // Keep sorted by date
        weightHistory.get(username).sort(Comparator.comparing(WeightEntry::getDate));
        System.out.println("[ProgressRepository] Added weight entry for " + username); // Debugging
    }

    public List<WeightEntry> getWeightHistory(String username) {
        return weightHistory.getOrDefault(username, new ArrayList<>())
                .stream()
                 .sorted(Comparator.comparing(WeightEntry::getDate)) // Ensure sorted
                .collect(Collectors.toList()); // Return a copy
    }

    public void addMeasurement(String username, Measurement entry) {
        measurementHistory.computeIfAbsent(username, k -> new ArrayList<>()).add(entry);
        // Keep sorted by date
        measurementHistory.get(username).sort(Comparator.comparing(Measurement::getDate));
        System.out.println("[ProgressRepository] Added measurement entry for " + username); // Debugging
    }

    public List<Measurement> getMeasurementHistory(String username) {
        return measurementHistory.getOrDefault(username, new ArrayList<>())
                .stream()
                 .sorted(Comparator.comparing(Measurement::getDate)) // Ensure sorted
                .collect(Collectors.toList()); // Return a copy
    }
}
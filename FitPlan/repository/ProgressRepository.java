package repository;

import model.Measurement;
import model.WeightEntry;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ProgressRepository {
    // Map<Username, List<WeightEntry>>
    private final Map<String, List<WeightEntry>> weightHistory = new ConcurrentHashMap<>();
    // Map<Username, List<Measurement>>
    private final Map<String, List<Measurement>> measurementHistory = new ConcurrentHashMap<>();

     // --- Mock Data ---
    public ProgressRepository() {
        // Add some mock progress for 'testuser'
        List<WeightEntry> testUserWeights = new ArrayList<>(Arrays.asList(
            new WeightEntry(LocalDate.now().minusDays(14), 80.5),
            new WeightEntry(LocalDate.now().minusDays(7), 79.8),
            new WeightEntry(LocalDate.now(), 79.5)
        ));
        weightHistory.put("testuser", testUserWeights);

        Map<String, Double> measurements1 = new HashMap<>();
        measurements1.put("Waist", 90.0);
        measurements1.put("Chest", 105.0);
        Measurement m1 = new Measurement(LocalDate.now().minusDays(10), measurements1);

        Map<String, Double> measurements2 = new HashMap<>();
        measurements2.put("Waist", 89.0);
        measurements2.put("Chest", 105.5);
        measurements2.put("Bicep L", 35.0);
        Measurement m2 = new Measurement(LocalDate.now(), measurements2);

        measurementHistory.put("testuser", new ArrayList<>(Arrays.asList(m1, m2)));
        System.out.println("[ProgressRepository] Initialized with mock progress data for 'testuser'."); // Debugging
    }
    // --- End Mock Data ---

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
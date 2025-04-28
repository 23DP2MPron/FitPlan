package FitPlan.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapter;

import FitPlan.model.Measurement;
import FitPlan.model.WeightEntry;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ProgressRepository {
    // Maps for in-memory data
    private Map<String, List<WeightEntry>> weightHistory;
    private Map<String, List<Measurement>> measurementHistory;
    
    // File paths for persistence
    private final String weightHistoryFilePath;
    private final String measurementsFilePath;
    
    // Gson for JSON serialization/deserialization
    private final Gson gson;

    public ProgressRepository() {
        this("data/weight_histories.json", "data/measurements.json");
    }

    public ProgressRepository(String weightHistoryFilePath, String measurementsFilePath) {
        // Setup custom gson with LocalDate adapter
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
            
        this.weightHistoryFilePath = weightHistoryFilePath;
        this.measurementsFilePath = measurementsFilePath;
        
        // Initialize data structures
        this.weightHistory = new HashMap<>();
        this.measurementHistory = new HashMap<>();
        
        // Ensure directory exists
        createDataDirectoryIfNotExists();
        
        // Load data from files
        loadFromFiles();
        System.out.println("[ProgressRepository] Initialized with weight histories for users: " + weightHistory.keySet());
    }
    
    private void createDataDirectoryIfNotExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("[ProgressRepository] Created data directory: " + directory.getAbsolutePath());
            } else {
                System.err.println("[ProgressRepository] Failed to create data directory: " + directory.getAbsolutePath());
            }
        }
    }

    private void loadFromFiles() {
        loadWeightHistories();
        loadMeasurementHistories();
    }
    
    private void loadWeightHistories() {
        File file = new File(weightHistoryFilePath);
        
        if (!file.exists()) {
            weightHistory = new HashMap<>();
            return;
        }
        
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, List<WeightEntry>>>() {}.getType();
            Map<String, List<WeightEntry>> loaded = gson.fromJson(reader, type);
            
            if (loaded != null) {
                weightHistory = loaded;
                
                // Ensure all lists are sorted by date
                for (List<WeightEntry> entries : weightHistory.values()) {
                    entries.sort(Comparator.comparing(WeightEntry::getDate));
                }
            } else {
                weightHistory = new HashMap<>();
            }
            
            System.out.println("[ProgressRepository] Loaded weight histories from file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[ProgressRepository] Error loading weight histories from file: " + e.getMessage());
            weightHistory = new HashMap<>();
        }
    }
    
    private void loadMeasurementHistories() {
        File file = new File(measurementsFilePath);
        
        if (!file.exists()) {
            measurementHistory = new HashMap<>();
            return;
        }
        
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, List<Measurement>>>() {}.getType();
            Map<String, List<Measurement>> loaded = gson.fromJson(reader, type);
            
            if (loaded != null) {
                measurementHistory = loaded;
                
                // Ensure all lists are sorted by date
                for (List<Measurement> entries : measurementHistory.values()) {
                    entries.sort(Comparator.comparing(Measurement::getDate));
                }
            } else {
                measurementHistory = new HashMap<>();
            }
            
            System.out.println("[ProgressRepository] Loaded measurement histories from file: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[ProgressRepository] Error loading measurement histories from file: " + e.getMessage());
            measurementHistory = new HashMap<>();
        }
    }

    private boolean saveWeightHistories() {
        File file = new File(weightHistoryFilePath);
        
        // Ensure parent directory exists
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(weightHistory, writer);
            System.out.println("[ProgressRepository] Saved weight histories to file: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("[ProgressRepository] Error saving weight histories to file: " + e.getMessage());
            return false;
        }
    }
    
    private boolean saveMeasurementHistories() {
        File file = new File(measurementsFilePath);
        
        // Ensure parent directory exists
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(measurementHistory, writer);
            System.out.println("[ProgressRepository] Saved measurement histories to file: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("[ProgressRepository] Error saving measurement histories to file: " + e.getMessage());
            return false;
        }
    }

    public void addWeightEntry(String username, WeightEntry entry) {
        if (username == null || entry == null) {
            System.err.println("[ProgressRepository] Cannot add weight entry: username or entry is null");
            return;
        }
        
        // Add to in-memory map
        weightHistory.computeIfAbsent(username, k -> new ArrayList<>()).add(entry);
        
        // Keep sorted by date
        weightHistory.get(username).sort(Comparator.comparing(WeightEntry::getDate));
        
        // Save to file
        boolean saved = saveWeightHistories();
        
        if (saved) {
            System.out.println("[ProgressRepository] Added weight entry for " + username + ": " + 
                              entry.getWeightKg() + " kg on " + entry.getDate());
        } else {
            System.err.println("[ProgressRepository] Failed to save weight entry for " + username);
        }
    }
    
    public List<WeightEntry> getWeightHistory(String username) {
        return weightHistory.getOrDefault(username, new ArrayList<>())
                .stream()
                .sorted(Comparator.comparing(WeightEntry::getDate))
                .collect(Collectors.toList()); // Return a copy
    }
    
    public void addMeasurement(String username, Measurement entry) {
        if (username == null || entry == null) {
            System.err.println("[ProgressRepository] Cannot add measurement: username or entry is null");
            return;
        }
        
        // Add to in-memory map
        measurementHistory.computeIfAbsent(username, k -> new ArrayList<>()).add(entry);
        
        // Keep sorted by date
        measurementHistory.get(username).sort(Comparator.comparing(Measurement::getDate));
        
        // Save to file
        boolean saved = saveMeasurementHistories();
        
        if (saved) {
            System.out.println("[ProgressRepository] Added measurement entry for " + username + " on " + entry.getDate());
        } else {
            System.err.println("[ProgressRepository] Failed to save measurement entry for " + username);
        }
    }
    
    public List<Measurement> getMeasurementHistory(String username) {
        return measurementHistory.getOrDefault(username, new ArrayList<>())
                .stream()
                .sorted(Comparator.comparing(Measurement::getDate))
                .collect(Collectors.toList()); // Return a copy
    }
    
    /**
     * TypeAdapter for LocalDate to handle serialization/deserialization
     */
    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(value));
            }
        }
        
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            String dateStr = in.nextString();
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateStr, formatter);
        }
    }
}
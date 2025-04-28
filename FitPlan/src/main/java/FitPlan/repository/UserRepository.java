package FitPlan.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import FitPlan.model.Measurement;
import FitPlan.model.User;
import FitPlan.model.WeightEntry;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserRepository {
    
    private Map<String, User> users;
    private Map<String, List<WeightEntry>> weightHistories;
    private Map<String, List<Measurement>> measurementHistories;
    private final String usersFilePath;
    private final String weightHistoryFilePath;
    private final String measurementsFilePath;
    private final Gson gson;

    public UserRepository() {
        this("data/users.json", "data/weight_histories.json", "data/measurements.json");
    }

    public UserRepository(String usersFilePath, String weightHistoryFilePath, String measurementsFilePath) {
        // Setup custom gson with LocalDate adapter
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
            
        this.usersFilePath = usersFilePath;
        this.weightHistoryFilePath = weightHistoryFilePath;
        this.measurementsFilePath = measurementsFilePath;
        
        // Initialize data structures
        this.users = new HashMap<>();
        this.weightHistories = new HashMap<>();
        this.measurementHistories = new HashMap<>();
        
        // Ensure directory exists
        createDataDirectoryIfNotExists();
        
        // Load data from files
        loadFromFiles();
        System.out.println("[UserRepository] Initialized with users: " + users.keySet());
    }
    
    private void createDataDirectoryIfNotExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created data directory: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create data directory: " + directory.getAbsolutePath());
            }
        }
    }

    private void loadFromFiles() {
        loadUsers();
        loadWeightHistories();
        loadMeasurementHistories();
    }
    
    private void loadUsers() {
        File file = new File(usersFilePath);
        
        if (!file.exists()) {
            users = new HashMap<>();
            return;
        }
        
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            Map<String, User> loadedUsers = gson.fromJson(reader, type);
            
            if (loadedUsers != null) {
                users = loadedUsers;
            } else {
                users = new HashMap<>();
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            users = new HashMap<>();
        }
    }
    
    private void loadWeightHistories() {
        File file = new File(weightHistoryFilePath);
        
        if (!file.exists()) {
            weightHistories = new HashMap<>();
            return;
        }
        
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, List<WeightEntry>>>() {}.getType();
            Map<String, List<WeightEntry>> loaded = gson.fromJson(reader, type);
            
            if (loaded != null) {
                weightHistories = loaded;
            } else {
                weightHistories = new HashMap<>();
            }
        } catch (IOException e) {
            System.err.println("Error loading weight histories from file: " + e.getMessage());
            weightHistories = new HashMap<>();
        }
    }
    
    private void loadMeasurementHistories() {
        File file = new File(measurementsFilePath);
        
        if (!file.exists()) {
            measurementHistories = new HashMap<>();
            return;
        }
        
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, List<Measurement>>>() {}.getType();
            Map<String, List<Measurement>> loaded = gson.fromJson(reader, type);
            
            if (loaded != null) {
                measurementHistories = loaded;
            } else {
                measurementHistories = new HashMap<>();
            }
        } catch (IOException e) {
            System.err.println("Error loading measurement histories from file: " + e.getMessage());
            measurementHistories = new HashMap<>();
        }
    }

    private boolean saveUsers() {
        try (Writer writer = new FileWriter(usersFilePath)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
            return false;
        }
    }
    
    private boolean saveWeightHistories() {
        try (Writer writer = new FileWriter(weightHistoryFilePath)) {
            gson.toJson(weightHistories, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving weight histories to file: " + e.getMessage());
            return false;
        }
    }
    
    private boolean saveMeasurementHistories() {
        try (Writer writer = new FileWriter(measurementsFilePath)) {
            gson.toJson(measurementHistories, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving measurement histories to file: " + e.getMessage());
            return false;
        }
    }
    
    private boolean saveAllData() {
        boolean usersSaved = saveUsers();
        boolean weightSaved = saveWeightHistories();
        boolean measurementsSaved = saveMeasurementHistories();
        
        return usersSaved && weightSaved && measurementsSaved;
    }

    public boolean saveUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false; 
        }
        
        // Don't allow overwriting existing users
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        // Add the user
        users.put(user.getUsername(), user);
        
        // Initialize empty histories
        weightHistories.put(user.getUsername(), new ArrayList<>());
        measurementHistories.put(user.getUsername(), new ArrayList<>());
        
        boolean saved = saveAllData();
        
        if (saved) {
            System.out.println("Saved user: " + user.getUsername());
        } else {
            System.err.println("Failed to save user: " + user.getUsername());
            // Rollback changes
            users.remove(user.getUsername());
            weightHistories.remove(user.getUsername());
            measurementHistories.remove(user.getUsername());
        }
        
        return saved;
    }

    public boolean updateUser(User user) {
        if (user == null || user.getUsername() == null || !users.containsKey(user.getUsername())) {
            return false; 
        }
        
        users.put(user.getUsername(), user); 
        boolean updated = saveUsers();
        
        if (updated) {
            System.out.println("Updated user: " + user.getUsername());
        } else {
            System.err.println("Failed to update user: " + user.getUsername());
            // Reload from file to restore original state
            loadUsers();
        }
        
        return updated;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
    
    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }
    
    public boolean deleteUser(String username) {
        if (!users.containsKey(username)) {
            return false;
        }
        
        // Remove user and their histories
        users.remove(username);
        weightHistories.remove(username);
        measurementHistories.remove(username);
        
        boolean deleted = saveAllData();
        
        if (deleted) {
            System.out.println("Deleted user: " + username);
        } else {
            System.err.println("Failed to delete user: " + username);
            // Reload from files to restore original state
            loadFromFiles();
        }
        
        return deleted;
    }
    
    /**
     * Adds a weight entry for a user
     * @param username The username
     * @param entry The weight entry to add
     * @return true if successful, false otherwise
     */
    public boolean addWeightEntry(String username, WeightEntry entry) {
        if (username == null || entry == null || !users.containsKey(username)) {
            return false;
        }
        
        // Get or create weight history list
        List<WeightEntry> history = weightHistories.computeIfAbsent(username, k -> new ArrayList<>());
        
        // Add entry
        history.add(entry);
        
        // Update user's current weight
        User user = users.get(username);
        user.setWeight(entry.getWeightKg());
        
        // Save both weight histories and updated user
        boolean historySaved = saveWeightHistories();
        boolean userSaved = saveUsers();
        
        boolean success = historySaved && userSaved;
        
        if (success) {
            System.out.println("Added weight entry for " + username + ": " + entry.getWeightKg() + " kg on " + entry.getDate());
        } else {
            System.err.println("Failed to add weight entry for " + username);
            // Reload from file to restore original state
            loadFromFiles();
        }
        
        return success;
    }
    
    /**
     * Gets the weight history for a user
     * @param username The username
     * @return List of weight entries, empty list if user not found
     */
    public List<WeightEntry> getWeightHistory(String username) {
        if (username == null || !users.containsKey(username)) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(weightHistories.getOrDefault(username, new ArrayList<>()));
    }
    
    /**
     * Adds a measurement for a user
     * @param username The username
     * @param measurement The measurement to add
     * @return true if successful, false otherwise
     */
    public boolean addMeasurement(String username, Measurement measurement) {
        if (username == null || measurement == null || !users.containsKey(username)) {
            return false;
        }
        
        // Get or create measurements list
        List<Measurement> measurements = measurementHistories.computeIfAbsent(username, k -> new ArrayList<>());
        
        // Add measurement
        measurements.add(measurement);
        
        // Save measurements
        boolean saved = saveMeasurementHistories();
        
        if (saved) {
            System.out.println("Added measurement for " + username + " on " + measurement.getDate());
        } else {
            System.err.println("Failed to add measurement for " + username);
            // Reload from file to restore original state
            loadMeasurementHistories();
        }
        
        return saved;
    }
    
    /**
     * Gets the measurement history for a user
     * @param username The username
     * @return List of measurements, empty list if user not found
     */
    public List<Measurement> getMeasurementHistory(String username) {
        if (username == null || !users.containsKey(username)) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(measurementHistories.getOrDefault(username, new ArrayList<>()));
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
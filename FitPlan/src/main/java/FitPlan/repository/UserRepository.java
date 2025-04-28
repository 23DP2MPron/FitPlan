package FitPlan.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import FitPlan.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    
    private Map<String, User> users;
    private final String filePath;
    private final Gson gson;

    public UserRepository() {
        this("users.json");
    }

    public UserRepository(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.users = new HashMap<>();
        
       
        loadFromFile();
        System.out.println("[UserRepository] Initialized with users: " + users.keySet());
    }

    private void loadFromFile() {
        File file = new File(filePath);
        
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

    
    private boolean saveToFile() {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
            return false;
        }
    }

    
    public boolean saveUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false; 
        }
        
        
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        users.put(user.getUsername(), user);
        boolean saved = saveToFile();
        
        if (saved) {
            System.out.println("Saved user: " + user.getUsername());
        } else {
            System.err.println("Failed to save user: " + user.getUsername());
           
            users.remove(user.getUsername());
        }
        
        return saved;
    }

    
    public boolean updateUser(User user) {
        if (user == null || user.getUsername() == null || !users.containsKey(user.getUsername())) {
            return false; 
        }
        
        users.put(user.getUsername(), user); 
        boolean updated = saveToFile();
        
        if (updated) {
            System.out.println("Updated user: " + user.getUsername());
        } else {
            System.err.println("Failed to update user: " + user.getUsername());
           
            loadFromFile();
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
        
        users.remove(username);
        boolean deleted = saveToFile();
        
        if (deleted) {
            System.out.println("Deleted user: " + username);
        } else {
            System.err.println("Failed to delete user: " + username);
            
            loadFromFile();
        }
        
        return deleted;
    }
}
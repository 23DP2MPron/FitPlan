package repository;

import model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    // Map<Username, User> for quick lookup
    private final Map<String, User> users = new HashMap<>();

    // --- Mock Data ---
    public UserRepository() {
        // Add some mock users for testing
        System.out.println("[UserRepository] Initialized with mock users: " + users.keySet()); // Debugging
    }
    // --- End Mock Data ---


    
    public boolean saveUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false; // Invalid user
        }
        // For a simple add, check if exists. If allowing updates, this logic changes.
        // If this is meant for registration, prevent overwriting.
         if (users.containsKey(user.getUsername())) {
             // If updating, allow overwrite:
             // users.put(user.getUsername(), user);
             // return true;

             // If registering, prevent overwrite:
             return false;
         }
        users.put(user.getUsername(), user);
        System.out.println("[UserRepository] Saved user: " + user.getUsername()); // Debugging
        return true;
    }

    
    public boolean updateUser(User user) {
        if (user == null || user.getUsername() == null || !users.containsKey(user.getUsername())) {
            return false; // User doesn't exist
        }
        users.put(user.getUsername(), user); // Overwrite existing user
        System.out.println("[UserRepository] Updated user: " + user.getUsername()); // Debugging
        return true;
    }


    
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
}
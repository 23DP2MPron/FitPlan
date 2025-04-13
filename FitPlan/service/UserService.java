package service;

import model.ActivityLevel;
import model.Gender;
import model.Goal;
import model.User;
import repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public Optional<User> registerUser(String username, String password, String name, int age, 
                                      double heightCm, double weightKg, Gender gender, 
                                      ActivityLevel activityLevel, Goal goal) {
        // Basic validation
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.err.println("[UserService] Error: Username and password cannot be empty.");
            return Optional.empty();
        }
        
        if (age <= 0 || heightCm <= 0 || weightKg <= 0) {
            System.err.println("[UserService] Error: Age, height, and weight must be positive.");
            return Optional.empty();
        }
        
        if (userRepository.existsByUsername(username)) {
            System.err.println("[UserService] Error: Username '" + username + "' is already taken.");
            return Optional.empty();
        }
        
        User newUser = new User(username, password, name, age, heightCm, weightKg, gender, activityLevel, goal);
        boolean saved = userRepository.saveUser(newUser);
        
        if (saved) {
            System.out.println("[UserService] User registered successfully: " + username);
            return Optional.of(newUser);
        } else {
            System.err.println("[UserService] Error: Failed to save user to repository.");
            return Optional.empty(); // Should not happen with current repo logic unless user existed
        }
    }
    
    /**
     * Alternative register method that sets default values for gender and activity level.
     * This maintains backward compatibility with code that uses the old constructor signature.
     */
    public Optional<User> registerUser(String username, String password, String name, int age, 
                                     double heightCm, double weightKg, Goal goal) {
        // Use default gender and activity level
        Gender defaultGender = Gender.MALE;
        ActivityLevel defaultActivity = ActivityLevel.MODERATE;
        
        System.out.println("[UserService] Using default gender (MALE) and activity level (MODERATE)");
        return registerUser(username, password, name, age, heightCm, weightKg, defaultGender, defaultActivity, goal);
    }
    
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // !! VERY Basic Password Check - HASH IN REAL APP !!
            if (user.getPassword().equals(password)) {
                System.out.println("[UserService] Login successful for user: " + username);
                return userOpt;
            } else {
                System.err.println("[UserService] Error: Incorrect password for user: " + username);
            }
        } else {
            System.err.println("[UserService] Error: User not found: " + username);
        }
        return Optional.empty();
    }
    
    public boolean updateUserGoal(User user, Goal newGoal) {
        if (user == null || newGoal == null) {
            return false;
        }
        user.setCurrentGoal(newGoal);
        // Persist the change back to the repository
        boolean updated = userRepository.updateUser(user);
        if (updated) {
            System.out.println("[UserService] Updated goal for " + user.getUsername() + " to " + newGoal.getDescription());
        } else {
            System.err.println("[UserService] Failed to update goal for " + user.getUsername() + " in repository.");
        }
        return updated;
    }
    
    public boolean updateUserWeight(User user, double newWeight) {
        if (user == null || newWeight <= 0) {
            return false;
        }
        user.setWeight(newWeight);
        // Persist the change back to the repository
        boolean updated = userRepository.updateUser(user);
        if (updated) {
            System.out.println("[UserService] Updated weight for " + user.getUsername() + " to " + newWeight + " kg");
        } else {
            System.err.println("[UserService] Failed to update weight for " + user.getUsername() + " in repository.");
        }
        return updated;
    }
    
    public boolean updateUserActivityLevel(User user, ActivityLevel newActivityLevel) {
        if (user == null || newActivityLevel == null) {
            return false;
        }
        user.setActivityLevel(newActivityLevel);
        // Persist the change back to the repository
        boolean updated = userRepository.updateUser(user);
        if (updated) {
            System.out.println("[UserService] Updated activity level for " + user.getUsername() + " to " + newActivityLevel);
        } else {
            System.err.println("[UserService] Failed to update activity level for " + user.getUsername() + " in repository.");
        }
        return updated;
    }
    
    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }
}
package service;

import model.ActivityLevel;
import model.Gender;
import model.Goal;
import model.User;
import repository.UserRepository;

import java.util.Optional;
import java.util.Objects;

public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository cannot be null");
    }
    
    public Optional<User> registerUser(String username, String password, String name, int age, 
                                     double heightCm, double weightKg, Gender gender, 
                                     ActivityLevel activityLevel, Goal goal) {
        // Parameter validation
        if (!validateRegistrationInput(username, password, name, age, heightCm, weightKg, gender, activityLevel, goal)) {
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
            return Optional.empty();
        }
    }
    
    public Optional<User> loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.err.println("[UserService] Error: Username and password cannot be empty.");
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
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
            System.err.println("[UserService] Error: User and Goal cannot be null.");
            return false;
        }
        return updateUserAttribute(user, newGoal, 
            () -> user.setCurrentGoal(newGoal), 
            "goal", 
            newGoal.getDescription());
    }
    
    public boolean updateUserWeight(User user, double newWeight) {
        if (user == null || newWeight <= 0) {
            System.err.println("[UserService] Error: User cannot be null and weight must be positive.");
            return false;
        }
        return updateUserAttribute(user, newWeight, 
            () -> user.setWeight(newWeight), 
            "weight", 
            newWeight + " kg");
    }
    
    public boolean updateUserActivityLevel(User user, ActivityLevel newActivityLevel) {
        if (user == null || newActivityLevel == null) {
            System.err.println("[UserService] Error: User and ActivityLevel cannot be null.");
            return false;
        }
        return updateUserAttribute(user, newActivityLevel, 
            () -> user.setActivityLevel(newActivityLevel), 
            "activity level", 
            newActivityLevel.toString());
    }
    
    public Optional<User> findUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("[UserService] Error: Username cannot be empty.");
            return Optional.empty();
        }
        return userRepository.findByUsername(username);
    }
    
    private boolean validateRegistrationInput(String username, String password, String name, int age, 
                                           double heightCm, double weightKg, Gender gender, 
                                           ActivityLevel activityLevel, Goal goal) {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("[UserService] Error: Username cannot be empty.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.err.println("[UserService] Error: Password cannot be empty.");
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            System.err.println("[UserService] Error: Name cannot be empty.");
            return false;
        }
        if (age <= 0) {
            System.err.println("[UserService] Error: Age must be positive.");
            return false;
        }
        if (heightCm <= 0) {
            System.err.println("[UserService] Error: Height must be positive.");
            return false;
        }
        if (weightKg <= 0) {
            System.err.println("[UserService] Error: Weight must be positive.");
            return false;
        }
        if (gender == null) {
            System.err.println("[UserService] Error: Gender cannot be null.");
            return false;
        }
        if (activityLevel == null) {
            System.err.println("[UserService] Error: Activity level cannot be null.");
            return false;
        }
        if (goal == null) {
            System.err.println("[UserService] Error: Goal cannot be null.");
            return false;
        }
        return true;
    }
    
    private boolean updateUserAttribute(User user, Object newValue, Runnable updateOperation, 
                                      String attributeName, String newValueDescription) {
        updateOperation.run();
        boolean updated = userRepository.updateUser(user);
        if (updated) {
            System.out.printf("[UserService] Updated %s for %s to %s%n", 
                attributeName, user.getUsername(), newValueDescription);
        } else {
            System.err.printf("[UserService] Failed to update %s for %s in repository.%n", 
                attributeName, user.getUsername());
        }
        return updated;
    }
}
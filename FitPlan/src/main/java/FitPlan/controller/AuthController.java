package FitPlan.controller;

import FitPlan.model.Goal;
import FitPlan.model.Gender;
import FitPlan.model.ActivityLevel;
import FitPlan.model.User;
import FitPlan.service.UserService;
import FitPlan.view.InputHelper;
import FitPlan.view.ViewRenderer;

import java.util.Optional;


public class AuthController {
    private final UserService userService;
    private final InputHelper inputHelper;
    private final ViewRenderer viewRenderer;

    public AuthController(UserService userService, InputHelper inputHelper, ViewRenderer viewRenderer) {
        this.userService = userService;
        this.inputHelper = inputHelper;
        this.viewRenderer = viewRenderer;
    }


    public User authenticate() {
        User authenticatedUser = null;
        boolean wantsToExit = false;

        while (authenticatedUser == null && !wantsToExit) {
            viewRenderer.displayLoginRegisterMenu();
            int choice = inputHelper.promptInt("Enter your choice", 1, 3);

            switch (choice) {
                case 1: // Login
                    authenticatedUser = handleLogin();
                    if (authenticatedUser == null) {
                        viewRenderer.displayError("Login failed. Please try again or register.");
                        inputHelper.waitForEnter(); // Pause before showing menu again
                    }
                    break;
                case 2: // Register
                    authenticatedUser = handleRegistration();
                     if (authenticatedUser == null) {
                         viewRenderer.displayError("Registration failed. Please check your input and try again.");
                         inputHelper.waitForEnter();
                     } else {
                         viewRenderer.displayMessage("Registration successful! Welcome, " + authenticatedUser.getName() + "!");
                         // Automatically logged in after registration
                     }
                    break;
                case 3: // Exit
                    viewRenderer.displayMessage("Exiting FitPlan. Goodbye!");
                    wantsToExit = true;
                    break;
                default: // Should not happen due to promptInt validation
                    viewRenderer.displayError("Invalid choice.");
                    break;
            }
            viewRenderer.clearConsole(); // Clear screen before next loop/main menu
        }

        return wantsToExit ? null : authenticatedUser; // Return null if exiting
    }

    private User handleLogin() {
        viewRenderer.displayMessage("--- Login ---");
        String username = inputHelper.promptString("Enter username:");
        String password = inputHelper.promptPassword("Enter password:"); // Use promptPassword

        Optional<User> userOpt = userService.loginUser(username, password);
        return userOpt.orElse(null);
    }

    private User handleRegistration() {
        viewRenderer.displayMessage("--- Register New User ---");
        String username = inputHelper.promptString("Choose a username:");
        // Basic check if username exists before asking for more details
         if (userService.findUser(username).isPresent()) {
             viewRenderer.displayError("Username '" + username + "' is already taken. Please try a different one.");
             return null;
         }

        String password = inputHelper.promptPassword("Choose a password:");
        String name = inputHelper.promptString("Enter your full name:");
        int age = inputHelper.promptInt("Enter your age:");
        double height = inputHelper.promptDouble("Enter your height (cm):");
        double weight = inputHelper.promptDouble("Enter your current weight (kg):");
        Gender gender = inputHelper.promptGenderSelection();
        ActivityLevel activityLevel = inputHelper.promptActivityLevelSelection();
        Goal goal = inputHelper.promptGoalSelection(); // Let user select goal

        Optional<User> userOpt = userService.registerUser(username, password, name, age, height, weight, gender,activityLevel, goal);
        return userOpt.orElse(null);
    }
}
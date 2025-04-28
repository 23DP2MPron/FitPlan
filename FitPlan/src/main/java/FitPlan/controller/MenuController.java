package FitPlan.controller;

import FitPlan.model.Goal;
import FitPlan.model.Measurement;
import FitPlan.model.User;
import FitPlan.model.WeightEntry;
import FitPlan.service.ProgressService;
import FitPlan.service.UserService;
import FitPlan.view.InputHelper;
import FitPlan.view.ViewRenderer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuController {
    private final UserService userService;
    private final ProgressService progressService;
    private final InputHelper inputHelper;
    private final ViewRenderer viewRenderer;
    private boolean userLoggedOut = false;

    public MenuController(UserService userService, ProgressService progressService, InputHelper inputHelper, ViewRenderer viewRenderer) {
        this.userService = userService;
        this.progressService = progressService;
        this.inputHelper = inputHelper;
        this.viewRenderer = viewRenderer;
    }


    public void showMainMenu(User currentUser) {
        boolean stayInMenu = true;
        userLoggedOut = false; // Reset logout flag each time menu is shown

        while (stayInMenu && !userLoggedOut) {
            viewRenderer.displayMainMenu(currentUser);
            int choice = inputHelper.promptInt("Enter your choice", 1, 5);

            switch (choice) {
                case 1: // Track Progress
                    showTrackProgressMenu(currentUser);
                    break;
                case 2: // View History
                    showViewHistoryMenu(currentUser);
                    break;
                case 3: // Change Goal
                    handleChangeGoal(currentUser);
                    inputHelper.waitForEnter();
                    break;
                case 4: // View Profile
                     viewRenderer.displayUserProfile(currentUser);
                     inputHelper.waitForEnter();
                    break;
                case 5: // Logout
                    viewRenderer.displayMessage("Logging out...");
                    userLoggedOut = true; // Signal logout to ConsoleRouter
                    stayInMenu = false; // Exit the main menu loop
                    break;
                default:
                    viewRenderer.displayError("Invalid choice.");
                    break;
            }
            if (stayInMenu && !userLoggedOut) viewRenderer.clearConsole(); // Clear before showing menu again unless logging out
        }
    }

     private void showTrackProgressMenu(User currentUser) {
        boolean stayInSubMenu = true;
         viewRenderer.clearConsole();

        while (stayInSubMenu) {
            viewRenderer.displayProgressMenu();
            int choice = inputHelper.promptInt("Enter your choice", 1, 3);

            switch (choice) {
                case 1: // Add Weight
                    handleAddWeightEntry(currentUser);
                     inputHelper.waitForEnter();
                    break;
                case 2: // Add Measurements
                    handleAddMeasurementEntry(currentUser);
                     inputHelper.waitForEnter();
                    break;
                case 3: // Back to Main Menu
                    stayInSubMenu = false;
                    break;
                default:
                    viewRenderer.displayError("Invalid choice.");
                    break;
            }
             if(stayInSubMenu) viewRenderer.clearConsole(); // Clear before showing sub-menu again
        }
    }

     private void showViewHistoryMenu(User currentUser) {
        boolean stayInSubMenu = true;
        viewRenderer.clearConsole();

        while (stayInSubMenu) {
            viewRenderer.displayHistoryMenu();
            int choice = inputHelper.promptInt("Enter your choice", 1, 3);

            switch (choice) {
                case 1: // Show Weight History
                     List<WeightEntry> weightHistory = progressService.getWeightHistory(currentUser.getUsername());
                     viewRenderer.displayWeightHistory(weightHistory);
                     inputHelper.waitForEnter();
                    break;
                case 2: // Show Measurement History
                     List<Measurement> measurementHistory = progressService.getMeasurementHistory(currentUser.getUsername());
                     viewRenderer.displayMeasurementHistory(measurementHistory);
                     inputHelper.waitForEnter();
                    break;
                case 3: // Back to Main Menu
                    stayInSubMenu = false;
                    break;
                default:
                    viewRenderer.displayError("Invalid choice.");
                    break;
            }
            if (stayInSubMenu) viewRenderer.clearConsole(); // Clear before showing sub-menu again
        }
    }


    private void handleAddWeightEntry(User currentUser) {
        viewRenderer.displayMessage("--- Add New Weight Entry ---");
        double weight = inputHelper.promptDouble("Enter weight (kg):");
        LocalDate date = inputHelper.promptDate("Enter date"); // Prompts for YYYY-MM-DD or blank for today

        progressService.addWeightEntry(currentUser.getUsername(), weight, date);
        viewRenderer.displayMessage("Weight entry added successfully!");
    }

     private void handleAddMeasurementEntry(User currentUser) {
        viewRenderer.displayMessage("--- Add New Body Measurements ---");
        LocalDate date = inputHelper.promptDate("Enter date");
        Map<String, Double> measurements = new HashMap<>();
        boolean addingMore = true;

        while (addingMore) {
            String part = inputHelper.promptString("Enter body part (e.g., Waist, Chest, Bicep L) or type 'done':");
            if (part.equalsIgnoreCase("done")) {
                addingMore = false;
            } else if (part.trim().isEmpty()){
                 viewRenderer.displayError("Body part name cannot be empty.");
            } else {
                double value = inputHelper.promptDouble("Enter measurement for '" + part + "' (cm):");
                measurements.put(part, value);
                viewRenderer.displayMessage("Added: " + part + " = " + value + " cm");
            }
        }

        if (!measurements.isEmpty()) {
            progressService.addMeasurement(currentUser.getUsername(), measurements, date);
            viewRenderer.displayMessage("Measurement set added successfully!");
        } else {
            viewRenderer.displayMessage("No measurements were added.");
        }
    }


    private void handleChangeGoal(User currentUser) {
        viewRenderer.displayMessage("--- Change Fitness Goal ---");
        System.out.println("Your current goal: " + (currentUser.getGoal() != null ? currentUser.getGoal().getDescription() : "Not Set"));
        Goal newGoal = inputHelper.promptGoalSelection(); // Reuse goal selection prompt

        if (userService.updateUserGoal(currentUser, newGoal)) {
            viewRenderer.displayMessage("Goal updated successfully to: " + newGoal.getDescription());
        } else {
            // Error message should be printed by UserService
            viewRenderer.displayError("Failed to update goal.");
        }
    }

     // Method for ConsoleRouter to check if logout occurred
    public boolean isUserLoggedOut() {
        return userLoggedOut;
    }

    // Reset the flag after ConsoleRouter acknowledges it
    public void resetLogoutFlag() {
        this.userLoggedOut = false;
    }
}
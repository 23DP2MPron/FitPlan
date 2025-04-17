package view;

import model.Measurement;
import model.User;
import model.WeightEntry;

import java.util.List;

public class ViewRenderer {

    public void displayWelcomeMessage() {
        clearConsole(); // Optional: Clears console for cleaner look
        printAsciiArt();
        System.out.println("\nWelcome to FitPlan - Your Console Fitness Tracker!");
        System.out.println("=================================================");
    }

    private void printAsciiArt() {
        System.out.println(
            " ______ _ _   _____  _             \n" +
            "|  ____(_) | |  __ \\| |           \n" +
            "| |__   _| |_| |__) | | __ _ _ __  \n" +
            "|  __| | | __|  ___/| |/ _` | '_ \\ \n" +
            "| |    | | |_| |    | | (_| | | | |\n" +
            "|_|    |_|\\__|_|    |_|\\__,_|_| |_|\n" 
        );
    }

    public void displayLoginRegisterMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("-------------------------");
    }

    public void displayMainMenu(User user) {
        clearConsole(); // Optional
        System.out.println("\n--- Main Menu ---");
        System.out.println("Welcome, " + user.getUsername() + "!");
        System.out.println("Current Goal: " + user.getGoal().getDescription());
        System.out.println("-------------------------");
        System.out.println("1. Track Progress (Weight/Measurements)");
        System.out.println("2. View Progress History");
        System.out.println("3. Change Goal");
        System.out.println("4. View Profile");
        System.out.println("5. Logout");
        System.out.println("-------------------------");
    }

    public void displayProgressMenu() {
        System.out.println("\n--- Track Progress ---");
        System.out.println("1. Add Weight Entry");
        System.out.println("2. Add Body Measurements");
        System.out.println("3. Back to Main Menu");
        System.out.println("-------------------------");
    }

    public void displayHistoryMenu() {
        System.out.println("\n--- View Progress History ---");
        System.out.println("1. Show Weight History");
        System.out.println("2. Show Measurement History");
        System.out.println("3. Back to Main Menu");
        System.out.println("-------------------------");
    }

    public void displayGenderOptions() {
        System.out.println("\nPlease select your gender:");
        String[] genders = {"MALE", "FEMALE", "OTHER"};
        for (int i = 0; i < genders.length; i++) {
            System.out.println((i + 1) + ". " + genders[i]);
        }
        System.out.println("-------------------------");
    }

    public void displayActivityLevelOptions() {
        System.out.println("\nPlease select your activity level:");
        String[] levels = {
            "SEDENTARY - Little to no exercise",
            "LIGHT - Light exercise 1-3 days per week",
            "MODERATE - Moderate exercise 3-5 days per week",
            "ACTIVE - Hard exercise 6-7 days per week",
            "VERY_ACTIVE - Very hard exercise & physical job or 2x training"
        };
        for (int i = 0; i < levels.length; i++) {
            System.out.println((i + 1) + ". " + levels[i]);
        }
        System.out.println("-------------------------");
    }

    public void displayGoalOptions() {
        System.out.println("\nPlease select your fitness goal:");
        String[] goals = {
            "Lose Weight",
            "Maintain Weight",
            "Gain Muscle",
            "Improve Endurance",
            "General Fitness"
        };
        for (int i = 0; i < goals.length; i++) {
            System.out.println((i + 1) + ". " + goals[i]);
        }
        System.out.println("-------------------------");
    }

    public void promptForGender() {
        System.out.print("Enter gender number (1-3): ");
    }

    public void promptForActivityLevel() {
        System.out.print("Enter activity level number (1-5): ");
    }

    public void displayMessage(String message) {
        System.out.println("\n>> " + message + " <<");
    }

    public void displayError(String error) {
        System.err.println("\n!! ERROR: " + error + " !!");
    }

    public void displayUserProfile(User user) {
        System.out.println("\n--- User Profile ---");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Age: " + user.getAge());
        System.out.println("Height: " + user.getHeight() + " cm");
        System.out.println("Initial Weight: " + user.getWeight() + " kg");
        System.out.println("Current Goal: " + user.getGoal().getDescription());
        System.out.println("--------------------");
    }

    public void displayWeightHistory(List<WeightEntry> history) {
        System.out.println("\n--- Weight History ---");
        if (history == null || history.isEmpty()) {
            System.out.println("No weight entries recorded yet.");
        } else {
            history.forEach(System.out::println); // Uses WeightEntry.toString()
        }
        System.out.println("----------------------");
    }

    public void displayMeasurementHistory(List<Measurement> history) {
        System.out.println("\n--- Measurement History ---");
        if (history == null || history.isEmpty()) {
            System.out.println("No measurement entries recorded yet.");
        } else {
            history.forEach(System.out::println); // Uses Measurement.toString()
        }
        System.out.println("---------------------------");
    }

    // Optional: Method to clear the console screen (platform dependent)
    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Assume Unix-like system (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
                // Runtime.getRuntime().exec("clear"); // Alternative, might not work in all terminals
            }
        } catch (final Exception e) {
            // Handle exceptions (e.g., process execution failed)
            System.out.println("\n-------------------------------\n"); // Fallback separator
        }
    }
}
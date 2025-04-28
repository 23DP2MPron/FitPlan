package FitPlan.view;

import FitPlan.model.Goal;
import FitPlan.model.Gender;
import FitPlan.model.ActivityLevel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptString(String prompt) {
        System.out.print(prompt + " ");
        String input = scanner.nextLine();
        // Basic check for empty input if required
        while (input.trim().isEmpty()) {
             System.out.print("Input cannot be empty. " + prompt + " ");
             input = scanner.nextLine();
        }
        return input;
    }

    public String promptPassword(String prompt) {
        System.out.print(prompt + " ");
        // In a real console app, might use Console class for masking
        // For simplicity here, just read the line.
        String password = scanner.nextLine();
         while (password.isEmpty()) { // Basic check: Password shouldn't be empty
             System.out.print("Password cannot be empty. " + prompt + " ");
             password = scanner.nextLine();
        }
        return password;
    }


    public int promptInt(String prompt) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " ");
            try {
                value = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
        return value;
    }

    public int promptInt(String prompt, int min, int max) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
             System.out.print(prompt + " (" + min + "-" + max + "): ");
             try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
        return value;
    }

    public double promptDouble(String prompt) {
        double value = 0.0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " ");
            try {
                value = Double.parseDouble(scanner.nextLine());
                 if (value > 0) { // Basic validation: often we need positive doubles
                    validInput = true;
                 } else {
                     System.out.println("Invalid input. Please enter a positive number.");
                 }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (e.g., 75.5).");
            }
        }
        return value;
    }

    public LocalDate promptDate(String prompt) {
        LocalDate date = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " (YYYY-MM-DD, leave blank for today): ");
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                return LocalDate.now(); // Default to today
            }
            try {
                date = LocalDate.parse(input); // Expects ISO format YYYY-MM-DD
                validInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

     public Goal promptGoalSelection() {
        Goal selectedGoal = null;
        while (selectedGoal == null) {
            Goal.displayOptions(); // Show the goal options
            String input = promptString("Enter goal number or name:");
            selectedGoal = Goal.fromString(input);
            if (selectedGoal == null) {
                System.out.println("Invalid goal selection. Please try again.");
            }
        }
        return selectedGoal;
    }

    public Gender promptGenderSelection() {
        Gender selectedGender = null;
        while (selectedGender == null) {
            System.out.println("Available genders:");
            System.out.println("1. Male");
            System.out.println("2. Female");
            String input = promptString("Enter Gender number or name (1/2 or male/female):");
            
            // Handle selection by number
            if (input.equals("1") || input.equalsIgnoreCase("male")) {
                selectedGender = Gender.MALE;
            } else if (input.equals("2") || input.equalsIgnoreCase("female")) {
                selectedGender = Gender.FEMALE;
            }
            
            if (selectedGender == null) {
                System.out.println("Invalid gender selection. Please try again.");
            }
        }
        return selectedGender;
    }

    public ActivityLevel promptActivityLevelSelection() {
        ActivityLevel selectedLevel = null;
        while (selectedLevel == null) {
            System.out.println("Activity levels:");
            System.out.println("1. Sedentary (little or no exercise)");
            System.out.println("2. Light (light exercise 1-3 days/week)");
            System.out.println("3. Moderate (moderate exercise 3-5 days/week)");
            System.out.println("4. Active (hard exercise 6-7 days/week)");
            System.out.println("5. Very Active (very hard exercise & physical job)");
            
            String input = promptString("Enter Activity level number or name (1-5):");
            
            // Handle selection by number or name
            if (input.equals("1") || input.equalsIgnoreCase("sedentary")) {
                selectedLevel = ActivityLevel.SEDENTARY;
            } else if (input.equals("2") || input.equalsIgnoreCase("light")) {
                selectedLevel = ActivityLevel.LIGHT;
            } else if (input.equals("3") || input.equalsIgnoreCase("moderate")) {
                selectedLevel = ActivityLevel.MODERATE;
            } else if (input.equals("4") || input.equalsIgnoreCase("active")) {
                selectedLevel = ActivityLevel.HIGH;
            } else if (input.equals("5") || input.equalsIgnoreCase("very active") || input.equalsIgnoreCase("veryactive")) {
                selectedLevel = ActivityLevel.VERY_HIGH;
            }
            
            if (selectedLevel == null) {
                System.out.println("Invalid activity level selection. Please try again.");
            }
        }
        return selectedLevel;
    }

     // Utility to consume the leftover newline character
    // Not always needed if using nextLine consistently, but can be helpful
    public void consumeRemainingLine() {
        // scanner.nextLine(); // Uncomment if facing issues with nextInt/nextDouble followed by nextLine
    }

    public void waitForEnter() {
         System.out.print("\nPress Enter to continue...");
         scanner.nextLine();
    }
}
package view;

import model.Goal;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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
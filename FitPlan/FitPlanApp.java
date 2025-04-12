import model.User;
import model.Gender;
import model.ActivityLevel;
import model.Goal;
import service.MacroCalculationService;

public class FitPlanApp {

    public static void main(String[] args) {
        // Create a test user: male, 100 kg, 184 cm, high activity level, goal to maintain weight
        User testUser = new User(
            18,              // Age: 18 years
            184.0,           // Height: 184 cm
            100.0,           // Weight: 100 kg
            Gender.MALE,     // Gender: Male
            Goal.LOSE,   // Goal: Maintain weight
            ActivityLevel.HIGH // Activity Level: High
        );

        // Create an instance of MacroCalculationService
        MacroCalculationService macroService = new MacroCalculationService();

        // Calculate and display the user's macros
        System.out.println("Calculating macros for the test user...");
        macroService.calculateAndDisplayMacros(testUser);
    }
}
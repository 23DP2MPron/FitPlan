package service;

import model.User;
import model.Gender;
import model.ActivityLevel;
import model.Goal;

public class MacroCalculationService {

    /**
     * Calculates TDEE using the Mifflin-St Jeor formula.
     * @param user The user object containing age, weight, height, gender, and activity level.
     * @return TDEE in calories.
     */
    public double calculateTDEEByMifflinStJeor(User user) {
        // Step 1: Calculate BMR (Basal Metabolic Rate)
        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }

        // Step 2: Multiply by the activity level
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        return bmr * activityMultiplier;
    }

    /**
     * Calculates TDEE using the Harris-Benedict formula.
     * @param user The user object containing age, weight, height, gender, and activity level.
     * @return TDEE in calories.
     */
    public double calculateTDEEByHarrisBenedict(User user) {
        // Step 1: Calculate BMR (Basal Metabolic Rate)
        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());
        } else {
            bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
        }

        // Step 2: Multiply by the activity level
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        return bmr * activityMultiplier;
    }

    /**
     * Determines the activity multiplier based on the user's activity level.
     * @param activityLevel The user's activity level.
     * @return The activity multiplier.
     */
    private double getActivityMultiplier(ActivityLevel activityLevel) {
        switch (activityLevel) {
            case SEDENTARY:
                return 1.2;
            case LIGHT:
                return 1.375;
            case MODERATE:
                return 1.55;
            case HIGH:
                return 1.725;
            case VERY_HIGH:
                return 1.9;
            default:
                return 1.2; // Default to sedentary if something goes wrong
        }
    }

    /**
     * Adjusts the TDEE based on the user's goal.
     * @param tdee The calculated TDEE.
     * @param goal The user's goal (LOSE_WEIGHT, GAIN_WEIGHT, MAINTAIN).
     * @return Adjusted calories based on the goal.
     */
    private double adjustCaloriesForGoal(double tdee, Goal goal) {
        switch (goal) {
            case LOSE:
                return tdee * 0.8; // Reduce by 20% for weight loss
            case GAIN:
                return tdee * 1.1; // Increase by 10% for weight gain
            case MAINTAIN:
                return tdee; // No adjustment for maintenance
            default:
                return tdee;
        }
    }

    /**
     * Calculates macronutrients based on the given calories.
     * Uses a 40% carbs, 30% protein, 30% fat split.
     * @param calories The daily calorie target.
     * @return An array of [protein, fat, carbs] in grams.
     */
    private double[] calculateMacros(double calories) {
        double protein = (calories * 0.3) / 4; // 30% of calories, 1g protein = 4 kcal
        double fat = (calories * 0.3) / 9;     // 30% of calories, 1g fat = 9 kcal
        double carbs = (calories * 0.4) / 4;   // 40% of calories, 1g carbs = 4 kcal
        return new double[]{protein, fat, carbs};
    }

    /**
     * Calculates and displays the user's macros using both Mifflin-St Jeor and Harris-Benedict formulas.
     * @param user The user object containing necessary data.
     */
    public void calculateAndDisplayMacros(User user) {
        // Calculate TDEE using both formulas
        double tdeeMifflin = calculateTDEEByMifflinStJeor(user);
        double tdeeHarris = calculateTDEEByHarrisBenedict(user);

        // Adjust calories based on the user's goal
        double caloriesMifflin = adjustCaloriesForGoal(tdeeMifflin, user.getGoal());
        double caloriesHarris = adjustCaloriesForGoal(tdeeHarris, user.getGoal());

        // Calculate macros for both calorie targets
        double[] macrosMifflin = calculateMacros(caloriesMifflin);
        double[] macrosHarris = calculateMacros(caloriesHarris);

        // Display results for Mifflin-St Jeor
        System.out.println("=== Mifflin-St Jeor Formula ===");
        System.out.printf("TDEE: %.0f kcal%n", tdeeMifflin);
        System.out.printf("Adjusted Calories (based on goal): %.0f kcal%n", caloriesMifflin);
        System.out.printf("Protein: %.0f g%n", macrosMifflin[0]);
        System.out.printf("Fat: %.0f g%n", macrosMifflin[1]);
        System.out.printf("Carbs: %.0f g%n", macrosMifflin[2]);
        System.out.println();

        // Display results for Harris-Benedict
        System.out.println("=== Harris-Benedict Formula ===");
        System.out.printf("TDEE: %.0f kcal%n", tdeeHarris);
        System.out.printf("Adjusted Calories (based on goal): %.0f kcal%n", caloriesHarris);
        System.out.printf("Protein: %.0f g%n", macrosHarris[0]);
        System.out.printf("Fat: %.0f g%n", macrosHarris[1]);
        System.out.printf("Carbs: %.0f g%n", macrosHarris[2]);
    }

    // Temporary main method for testing
    public static void main(String[] args) {
        // Create a test user
        User user = new User(30, 175.0, 70.0, Gender.MALE, Goal.MAINTAIN, ActivityLevel.MODERATE);
        MacroCalculationService service = new MacroCalculationService();
        service.calculateAndDisplayMacros(user);
    }
}
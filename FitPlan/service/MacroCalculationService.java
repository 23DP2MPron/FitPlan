package service;

import model.User;
import model.Gender;
import model.ActivityLevel;
import model.Goal;

public class MacroCalculationService {

    /**
     * Calculates TDEE using the Mifflin-St Jeor formula.
     */
    public double calculateTDEEByMifflinStJeor(User user) {
        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        return bmr * activityMultiplier;
    }

    /**
     * Calculates TDEE using the Harris-Benedict formula.
     */
    public double calculateTDEEByHarrisBenedict(User user) {
        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());
        } else {
            bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
        }
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        return bmr * activityMultiplier;
    }

    /**
     * Calculates TDEE using the Owen Equation.
     */
    public double calculateTDEEByOwen(User user) {
        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 879 + 10.2 * user.getWeight();
        } else {
            bmr = 795 + 7.18 * user.getWeight();
        }
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        return bmr * activityMultiplier;
    }

    // Returns the activity multiplier based on the user's activity level
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
                return 1.2;
        }
    }

    // Adjusts TDEE based on the user's goal
    private double adjustCaloriesForGoal(double tdee, Goal goal) {
        switch (goal) {
            case LOSE:
                return tdee * 0.85; // Reduce by 15% for weight loss
            case GAIN:
                return tdee * 1.1; // Increase by 10% for weight gain
            case MAINTAIN:
                return tdee; // No adjustment for maintenance
            default:
                return tdee;
        }
    }

    // Calculates macros based on weight: protein 1.4-2.2 g/kg, fat 0.8-1.5 g/kg, carbs the rest
    private double[][] calculateMacros(double minCalories, double maxCalories, double weight) {
        // Protein: 1.4 to 2.2 g per kg of body weight
        double minProtein = weight * 1.4;
        double maxProtein = weight * 2.2;

        // Fat: 0.8 to 1.3 g per kg of body weight
        double minFat = weight * 0.8;
        double maxFat = weight * 1.3;

        // Calculate calories from protein and fat (using min and max values)
        double minProteinCalories = minProtein * 4; // 1 g protein = 4 kcal
        double maxProteinCalories = maxProtein * 4;
        double minFatCalories = minFat * 9; // 1 g fat = 9 kcal
        double maxFatCalories = maxFat * 9;

        // Calculate remaining calories for carbs
        // Use minCalories and maxCalories to get the full range
        double minCarbsCalories = minCalories - (maxProteinCalories + maxFatCalories);
        double maxCarbsCalories = maxCalories - (minProteinCalories + minFatCalories);

        // Convert carb calories to grams
        double minCarbs = minCarbsCalories / 4; // 1 g carbs = 4 kcal
        double maxCarbs = maxCarbsCalories / 4;

        // Ensure carbs are not negative
        if (minCarbs < 0) minCarbs = 0;
        if (maxCarbs < 0) maxCarbs = 0;

        return new double[][]{{minProtein, maxProtein}, {minFat, maxFat}, {minCarbs, maxCarbs}};
    }

    /**
     * Calculates and displays the user's daily calorie needs and macros.
     */
    public void calculateAndDisplayMacros(User user) {
        // Calculate TDEE using all three formulas
        double tdeeMifflin = calculateTDEEByMifflinStJeor(user);
        double tdeeHarris = calculateTDEEByHarrisBenedict(user);
        double tdeeOwen = calculateTDEEByOwen(user);

        // Adjust calories based on the user's goal
        double caloriesMifflin = adjustCaloriesForGoal(tdeeMifflin, user.getGoal());
        double caloriesHarris = adjustCaloriesForGoal(tdeeHarris, user.getGoal());
        double caloriesOwen = adjustCaloriesForGoal(tdeeOwen, user.getGoal());

        // Display daily calorie needs for each formula
        System.out.println("Daily Calorie Needs:");
        System.out.printf("Mifflin-St Jeor Formula: %.0f kcal%n", caloriesMifflin);
        System.out.printf("Harris-Benedict Formula: %.0f kcal%n", caloriesHarris);
        System.out.printf("Owen Equation: %.0f kcal%n", caloriesOwen);
        System.out.println();

        // Find the min and max calories across all formulas
        double minCalories = Math.min(Math.min(caloriesMifflin, caloriesHarris), caloriesOwen);
        double maxCalories = Math.max(Math.max(caloriesMifflin, caloriesHarris), caloriesOwen);

        // Calculate macros based on the min and max calories
        double[][] macros = calculateMacros(minCalories, maxCalories, user.getWeight());

        // Display the generalized macro ranges
        System.out.println("Recommended Macro Ranges:");
        System.out.printf("Protein (1.4–2.2 g/kg): %.0f–%.0f g%n", macros[0][0], macros[0][1]);
        System.out.printf("Fat (0.8–1.3 g/kg): %.0f–%.0f g%n", macros[1][0], macros[1][1]);
        System.out.printf("Carbs (remaining calories): %.0f–%.0f g%n", macros[2][0], macros[2][1]);
        System.out.println();

        // Add a note about approximate values
        System.out.println("Note: These are approximate values. Individual needs may vary based on metabolism, activity, and other factors.");
    }
}
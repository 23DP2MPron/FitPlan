import model.Gender;
import service.WorkoutPlanService;
import java.util.Scanner;

public class FitPlanApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display workout plan options
        System.out.println("Welcome to FitPlan!");
        System.out.println("Select the workout plan:");
        System.out.println("1. For Men");
        System.out.println("2. For Women");
        System.out.print("Enter your choice (1 or 2): ");
        int workoutChoice = scanner.nextInt();

        // Create WorkoutPlanService instance
        WorkoutPlanService workoutService = new WorkoutPlanService();

        // Display the selected workout plan
        if (workoutChoice == 1) {
            workoutService.displayWorkoutPlan(Gender.MALE);
        } else if (workoutChoice == 2) {
            workoutService.displayWorkoutPlan(Gender.FEMALE);
        } else {
            System.out.println("Invalid choice. Please select 1 or 2.");
        }

        scanner.close();
    }
}
import model.Gender;
import service.WorkoutPlanService;
import java.util.Scanner;
import view.WorkoutPlanRenderer;

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

        // Create WorkoutPlanService and WorkoutPlanRenderer instances
        WorkoutPlanService workoutService = new WorkoutPlanService();
        WorkoutPlanRenderer renderer = new WorkoutPlanRenderer();

        // Get and display the selected workout plan
        try {
            if (workoutChoice == 1) {
                renderer.renderWorkoutPlan(workoutService.getWorkoutPlan(Gender.MALE));
            } else if (workoutChoice == 2) {
                renderer.renderWorkoutPlan(workoutService.getWorkoutPlan(Gender.FEMALE));
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
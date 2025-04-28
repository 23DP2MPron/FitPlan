package FitPlan.view;

import FitPlan.model.WorkoutPlan;
import FitPlan.model.WorkoutSession;
import FitPlan.model.Exercise;

public class WorkoutPlanRenderer {

    public void renderWorkoutPlan(WorkoutPlan plan) {
        // Display general program information
        System.out.println(plan.getProgramName());
        printDivider(60); // Set divider length to 60 characters
        System.out.println("Rest Between Sets: " + plan.getRestBetweenSets());
        System.out.println("Rest Between Exercises: " + plan.getRestBetweenExercises());
        System.out.println("Training Frequency: " + plan.getTrainingFrequency());
        System.out.println("Weekly Schedule: " + plan.getWeeklySchedule());
        System.out.println();

        // Display workout sessions
        for (WorkoutSession session : plan.getSessions()) {
            System.out.println(session.getName());
            
            // Calculate maximum column lengths
            int maxExerciseLength = "Exercise".length(); // Initial length of the header
            int maxSetsAndRepsLength = "Sets and Reps".length(); // Initial length of the header

            // Find the maximum length of strings in each column
            for (Exercise exercise : session.getExercises()) {
                maxExerciseLength = Math.max(maxExerciseLength, exercise.getName().length());
                maxSetsAndRepsLength = Math.max(maxSetsAndRepsLength, exercise.getSetsAndReps().length());
            }

            // Add a small padding for better appearance
            maxExerciseLength += 2;
            maxSetsAndRepsLength += 2;

            // Display the table
            printDivider(maxExerciseLength + maxSetsAndRepsLength + 3); // 3 for "|", space, and another "|"
            System.out.println("| " + padRight("Exercise", maxExerciseLength) + "| " + padRight("Sets and Reps", maxSetsAndRepsLength) + "|");
            printDivider(maxExerciseLength + maxSetsAndRepsLength + 3);
            
            for (Exercise exercise : session.getExercises()) {
                System.out.println("| " + padRight(exercise.getName(), maxExerciseLength) + "| " + padRight(exercise.getSetsAndReps(), maxSetsAndRepsLength) + "|");
            }
            printDivider(maxExerciseLength + maxSetsAndRepsLength + 3);
            System.out.println();
        }

        // Display tips
        System.out.println("Tips for Using the Program");
        printDivider(60);
        for (String tip : plan.getTips()) {
            System.out.println("- " + tip);
        }
        System.out.println();
    }

    private String padRight(String s, int n) {
        // Always pad with spaces to the specified length n, even if the string is longer
        return String.format("%-" + n + "s", s);
    }

    private void printDivider(int length) {
        // Print a divider of the specified length
        System.out.println("-".repeat(length));
    }
}
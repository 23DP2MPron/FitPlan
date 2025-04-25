package service;

import model.Gender;

public class WorkoutPlanService {
    /**
     * Displays the workout plan based on the user's gender.
     */
    public void displayWorkoutPlan(Gender gender) {
        // General program information
        System.out.println("Full-Body Workout Program");
        System.out.println("------------------------");
        System.out.println("Rest Between Sets: 2 minutes");
        System.out.println("Rest Between Exercises: 3 minutes");
        System.out.println("Training Frequency: 3 times per week");
        System.out.println("Weekly Schedule: Workout A, Rest, Workout B, Rest, Workout A, Rest, Rest, Workout B");
        System.out.println();

        // Display workout plan based on gender
        if (gender == Gender.MALE) {
            displayMensWorkoutPlan();
        } else if (gender == Gender.FEMALE) {
            displayWomensWorkoutPlan();
        } else {
            System.out.println("Workout plans are available for male and female users only.");
            return;
        }

        // Display tips for using the program
        displayTips();
    }

    private void displayMensWorkoutPlan() {
        System.out.println("Workout Plan for Men");
        System.out.println();

        // Workout A
        System.out.println("Workout A");
        System.out.println("------------------------");
        System.out.println("| Exercise                     | Sets and Reps       |");
        System.out.println("------------------------");
        System.out.println("| Bench Press                 | 3 sets of 6-10 reps  |");
        System.out.println("| Back Squat                  | 3 sets of 6-10 reps  |");
        System.out.println("| Pull-Ups                    | 3 sets of 6-10 reps  |");
        System.out.println("| Leg Curl                    | 3 sets of 10-15 reps |");
        System.out.println("| Seated Overhead Press       | 2-3 sets of 6-10 reps|");
        System.out.println("| French Press                | 2-3 sets of 10-15 reps|");
        System.out.println("| Incline Bench Dumbbell Curl | 2-3 sets of 8-10 reps|");
        System.out.println("------------------------");
        System.out.println();

        // Workout B
        System.out.println("Workout B");
        System.out.println("------------------------");
        System.out.println("| Exercise                     | Sets and Reps       |");
        System.out.println("------------------------");
        System.out.println("| Deadlift                    | 3 sets of 6-10 reps  |");
        System.out.println("| Incline Dumbbell Press      | 2-3 sets of 6-12 reps|");
        System.out.println("| Bulgarian Split Squat       | 3 sets of 6-10 reps  |");
        System.out.println("| Bent-Over Row               | 3 sets of 6-12 reps  |");
        System.out.println("| Lateral Raises              | 2-3 sets of 10-15 reps|");
        System.out.println("| Face Pull                   | 2-3 sets of 10-15 reps|");
        System.out.println("| Calf Raises                 | 3 sets of 10-15 reps |");
        System.out.println("------------------------");
        System.out.println();
    }

    private void displayWomensWorkoutPlan() {
        System.out.println("Workout Plan for Women");
        System.out.println();

        // Workout A
        System.out.println("Workout A");
        System.out.println("------------------------");
        System.out.println("| Exercise                     | Sets and Reps       |");
        System.out.println("------------------------");
        System.out.println("| Squats (Dumbbells/Barbell)  | 3 sets of 8-12 reps  |");
        System.out.println("| Glute Bridge                | 3 sets of 10-15 reps |");
        System.out.println("| Romanian Deadlift           | 3 sets of 8-12 reps  |");
        System.out.println("| Seated Dumbbell Overhead Press | 2-3 sets of 10-12 reps|");
        System.out.println("| Rear Delt Flyes             | 2-3 sets of 12-15 reps|");
        System.out.println("| Pull-Ups (with band/machine)| 3 sets to failure or 6-10 reps|");
        System.out.println("| Plank                       | 3 sets of 30-60 seconds|");
        System.out.println("------------------------");
        System.out.println();

        // Workout B
        System.out.println("Workout B");
        System.out.println("------------------------");
        System.out.println("| Exercise                     | Sets and Reps       |");
        System.out.println("------------------------");
        System.out.println("| Reverse Lunges (Dumbbells)  | 3 sets of 8-10 reps per leg|");
        System.out.println("| Sumo Deadlift (Dumbbells)   | 3 sets of 8-12 reps  |");
        System.out.println("| Glute Kickback (Cable/Band) | 3 sets of 15-20 reps |");
        System.out.println("| Seated Row or Dumbbell Row  | 2-3 sets of 10-12 reps|");
        System.out.println("| Lateral Raises              | 2-3 sets of 12-15 reps|");
        System.out.println("| Crunches                    | 3 sets of 15-20 reps |");
        System.out.println("| Calf Raises                 | 2-3 sets of 15-20 reps|");
        System.out.println("------------------------");
        System.out.println();
    }

    private void displayTips() {
        System.out.println("Tips for Using the Program");
        System.out.println("------------------------");
        System.out.println("- For Beginners: Start with Workout A for all three weekly sessions to build a foundation. Focus on proper form rather than heavy weights.");
        System.out.println("- For Experienced Athletes: Alternate between Workout A and Workout B as per the weekly schedule to ensure balanced muscle development.");
        System.out.println("- Warm-Up: Perform a 5-10 minute dynamic warm-up (e.g., light cardio, mobility exercises) before each session.");
        System.out.println("- Cool-Down: Stretch major muscle groups (quads, hamstrings, chest, shoulders) for 5-10 minutes after training.");
        System.out.println("- Progression: Gradually increase weights or reps as you get stronger, but avoid sacrificing form.");
        System.out.println("- Rest and Recovery: Ensure you get enough sleep and nutrition to support recovery between workouts.");
        System.out.println();
    }
}

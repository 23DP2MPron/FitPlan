package service;

import model.Gender;
import model.WorkoutPlan;
import model.WorkoutSession;
import model.Exercise;

import java.util.Arrays;
import java.util.List;

public class WorkoutPlanService {
    /**
     * Returns the workout plan based on the user's gender.
     */
    public WorkoutPlan getWorkoutPlan(Gender gender) {
        // General program information
        String programName = "Full-Body Workout Program";
        String restBetweenSets = "2 minutes";
        String restBetweenExercises = "3 minutes";
        String trainingFrequency = "3 times per week";
        String weeklySchedule = "Workout A, Rest, Workout B, Rest, Workout A, Rest, Rest, Workout B";

        // Tips for using the program
        List<String> tips = Arrays.asList(
                "For Beginners: Start with Workout A for all three weekly sessions to build a foundation. Focus on proper form rather than heavy weights.",
                "For Experienced Athletes: Alternate between Workout A and Workout B as per the weekly schedule to ensure balanced muscle development.",
                "Warm-Up: Perform a 5-10 minute dynamic warm-up (e.g., light cardio, mobility exercises) before each session.",
                "Cool-Down: Stretch major muscle groups (quads, hamstrings, chest, shoulders) for 5-10 minutes after training.",
                "Progression: Gradually increase weights or reps as you get stronger, but avoid sacrificing form.",
                "Rest and Recovery: Ensure you get enough sleep and nutrition to support recovery between workouts."
        );

        // Create workout sessions based on gender
        List<WorkoutSession> sessions;
        if (gender == Gender.MALE) {
            sessions = getMensWorkoutSessions();
        } else if (gender == Gender.FEMALE) {
            sessions = getWomensWorkoutSessions();
        } else {
            throw new IllegalArgumentException("Workout plans are available for male and female users only.");
        }

        // Return the workout plan
        return new WorkoutPlan(programName, restBetweenSets, restBetweenExercises,
                trainingFrequency, weeklySchedule, sessions, tips);
    }

    private List<WorkoutSession> getMensWorkoutSessions() {
        // Workout A
        List<Exercise> workoutAExercises = Arrays.asList(
                new Exercise("Bench Press", "3 sets of 6-10 reps"),
                new Exercise("Back Squat", "3 sets of 6-10 reps"),
                new Exercise("Pull-Ups", "3 sets of 6-10 reps"),
                new Exercise("Leg Curl", "3 sets of 10-15 reps"),
                new Exercise("Seated Overhead Press", "2-3 sets of 6-10 reps"),
                new Exercise("French Press", "2-3 sets of 10-15 reps"),
                new Exercise("Incline Bench Dumbbell Curl", "2-3 sets of 8-10 reps")
        );
        WorkoutSession workoutA = new WorkoutSession("Workout A", workoutAExercises);

        // Workout B
        List<Exercise> workoutBExercises = Arrays.asList(
                new Exercise("Deadlift", "3 sets of 6-10 reps"),
                new Exercise("Incline Dumbbell Press", "2-3 sets of 6-12 reps"),
                new Exercise("Bulgarian Split Squat", "3 sets of 6-10 reps"),
                new Exercise("Bent-Over Row", "3 sets of 6-12 reps"),
                new Exercise("Lateral Raises", "2-3 sets of 10-15 reps"),
                new Exercise("Face Pull", "2-3 sets of 10-15 reps"),
                new Exercise("Calf Raises", "3 sets of 10-15 reps")
        );
        WorkoutSession workoutB = new WorkoutSession("Workout B", workoutBExercises);

        return Arrays.asList(workoutA, workoutB);
    }

    private List<WorkoutSession> getWomensWorkoutSessions() {
        // Workout A
        List<Exercise> workoutAExercises = Arrays.asList(
                new Exercise("Squats (Dumbbells/Barbell)", "3 sets of 8-12 reps"),
                new Exercise("Glute Bridge", "3 sets of 10-15 reps"),
                new Exercise("Romanian Deadlift", "3 sets of 8-12 reps"),
                new Exercise("Seated Dumbbell Overhead Press", "2-3 sets of 10-12 reps"),
                new Exercise("Rear Delt Flyes", "2-3 sets of 12-15 reps"),
                new Exercise("Pull-Ups (with band/machine)", "3 sets to failure or 6-10 reps"),
                new Exercise("Plank", "3 sets of 30-60 seconds")
        );
        WorkoutSession workoutA = new WorkoutSession("Workout A", workoutAExercises);

        // Workout B
        List<Exercise> workoutBExercises = Arrays.asList(
                new Exercise("Reverse Lunges (Dumbbells)", "3 sets of 8-10 reps per leg"),
                new Exercise("Sumo Deadlift (Dumbbells)", "3 sets of 8-12 reps"),
                new Exercise("Glute Kickback (Cable/Band)", "3 sets of 15-20 reps"),
                new Exercise("Seated Row or Dumbbell Row", "2-3 sets of 10-12 reps"),
                new Exercise("Lateral Raises", "2-3 sets of 12-15 reps"),
                new Exercise("Crunches", "3 sets of 15-20 reps"),
                new Exercise("Calf Raises", "2-3 sets of 15-20 reps")
        );
        WorkoutSession workoutB = new WorkoutSession("Workout B", workoutBExercises);

        return Arrays.asList(workoutA, workoutB);
    }
}

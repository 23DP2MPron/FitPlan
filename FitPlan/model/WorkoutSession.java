package model;

import java.util.List;

public class WorkoutSession {
    private String name;
    private List<Exercise> exercises;

    public WorkoutSession(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}

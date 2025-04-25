package model;

import java.util.List;

public class WorkoutPlan {
    private String programName;
    private String restBetweenSets;
    private String restBetweenExercises;
    private String trainingFrequency;
    private String weeklySchedule;
    private List<WorkoutSession> sessions;
    private List<String> tips;

    public WorkoutPlan(String programName, String restBetweenSets, String restBetweenExercises,
                      String trainingFrequency, String weeklySchedule,
                      List<WorkoutSession> sessions, List<String> tips) {
        this.programName = programName;
        this.restBetweenSets = restBetweenSets;
        this.restBetweenExercises = restBetweenExercises;
        this.trainingFrequency = trainingFrequency;
        this.weeklySchedule = weeklySchedule;
        this.sessions = sessions;
        this.tips = tips;
    }

    public String getProgramName() {
        return programName;
    }

    public String getRestBetweenSets() {
        return restBetweenSets;
    }

    public String getRestBetweenExercises() {
        return restBetweenExercises;
    }

    public String getTrainingFrequency() {
        return trainingFrequency;
    }

    public String getWeeklySchedule() {
        return weeklySchedule;
    }

    public List<WorkoutSession> getSessions() {
        return sessions;
    }

    public List<String> getTips() {
        return tips;
    }
}

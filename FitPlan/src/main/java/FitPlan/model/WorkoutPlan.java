package FitPlan.model;

import java.util.List;

public class WorkoutPlan {
    private String programName;
    private String restBetweenSets;
    private String restBetweenExercises;
    private String trainingFrequency;
    private String weeklySchedule;
    private List<WorkoutSession> sessions;
    private List<String> tips;
    
    public WorkoutPlan() {
    }

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
    
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getRestBetweenSets() {
        return restBetweenSets;
    }
    
    public void setRestBetweenSets(String restBetweenSets) {
        this.restBetweenSets = restBetweenSets;
    }

    public String getRestBetweenExercises() {
        return restBetweenExercises;
    }
    
    public void setRestBetweenExercises(String restBetweenExercises) {
        this.restBetweenExercises = restBetweenExercises;
    }

    public String getTrainingFrequency() {
        return trainingFrequency;
    }
    
    public void setTrainingFrequency(String trainingFrequency) {
        this.trainingFrequency = trainingFrequency;
    }

    public String getWeeklySchedule() {
        return weeklySchedule;
    }
    
    public void setWeeklySchedule(String weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public List<WorkoutSession> getSessions() {
        return sessions;
    }
    
    public void setSessions(List<WorkoutSession> sessions) {
        this.sessions = sessions;
    }

    public List<String> getTips() {
        return tips;
    }
    
    public void setTips(List<String> tips) {
        this.tips = tips;
    }
    
    @Override
    public String toString() {
        return "WorkoutPlan{" +
                "programName='" + programName + '\'' +
                ", trainingFrequency='" + trainingFrequency + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
package FitPlan.model;

public class Exercise {
    private String name;
    private String setsAndReps;

    public Exercise(String name, String setsAndReps) {
        this.name = name;
        this.setsAndReps = setsAndReps;
    }

    public String getName() {
        return name;
    }

    public String getSetsAndReps() {
        return setsAndReps;
    }
}

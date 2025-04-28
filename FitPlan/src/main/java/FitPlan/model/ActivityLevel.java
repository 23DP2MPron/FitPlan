package FitPlan.model;

public enum ActivityLevel {
    SEDENTARY("Little to no exercise"),
    LIGHT("Light exercise 1-3 days per week"),
    MODERATE("Moderate exercise 3-5 days per week"),
    HIGH("Hard exercise 6-7 days per week"),
    VERY_HIGH("Very hard exercise & physical job or 2x training");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
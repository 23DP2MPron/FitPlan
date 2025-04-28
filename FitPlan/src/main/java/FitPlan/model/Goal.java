package FitPlan.model;

public enum Goal {
    LOSE("Weight Loss"),
    GAIN("Muscle Gain"),
    MAINTAIN("Maintenance");

    private final String description;

    Goal(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Goal fromString(String text) {
        for (Goal b : Goal.values()) {
            if (b.description.equalsIgnoreCase(text) || b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        // Find by number if possible
        try {
            int index = Integer.parseInt(text) - 1;
            if (index >= 0 && index < Goal.values().length) {
                return Goal.values()[index];
            }
        } catch (NumberFormatException e) {
            // Ignore, not a number
        }
        return null; // Or throw an exception
    }

     public static void displayOptions() {
        System.out.println("Available Goals:");
        int i = 1;
        for (Goal goal : Goal.values()) {
            System.out.println(i + ". " + goal.getDescription());
            i++;
        }
    }
}
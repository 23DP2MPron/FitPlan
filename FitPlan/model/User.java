package model;

public class User {
    private String username; // Unique identifier
    private String password; // In a real app, this would be hashed
    private String name;
    private int age;
    private double heightCm;
    private double weightKg; // Changed from initialWeightKg
    private Gender gender; // Added gender
    private ActivityLevel activityLevel; // Added activity level
    private Goal currentGoal;

    // Constructor for registration
    public User(String username, String password, String name, int age, double heightCm, 
                double weightKg, Gender gender, ActivityLevel activityLevel, Goal currentGoal) {
        this.username = username;
        this.password = password; // Store hash in real app
        this.name = name;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.currentGoal = currentGoal;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() { // Only for simple check in this example
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return heightCm;
    }

    public double getWeight() {
        return weightKg;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public Goal getGoal() {
        return currentGoal;
    }

    // Setters for changeable fields
    public void setWeight(double weightKg) {
        this.weightKg = weightKg;
    }
    
    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", heightCm=" + heightCm +
               ", weightKg=" + weightKg +
               ", gender=" + gender +
               ", activityLevel=" + activityLevel +
               ", currentGoal=" + (currentGoal != null ? currentGoal.getDescription() : "Not Set") +
               '}';
    }
}
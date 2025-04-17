package model;

public class User {
    private String username;
    private String password; // Note: In a real app, store hashed password
    private String name;
    private int age;
    private double height; // in cm
    private double weight; // in kg
    private Gender gender;
    private ActivityLevel activityLevel;
    private Goal currentGoal;

    public User(String username, String password, String name, int age, double height, double weight, 
                Gender gender, ActivityLevel activityLevel, Goal currentGoal) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.currentGoal = currentGoal;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Goal getGoal() {
        return currentGoal;
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
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                ", activityLevel=" + activityLevel +
                ", currentGoal=" + currentGoal +
                '}';
    }
}
package model;

public class User {
    private int age;
    private double height;
    private double weight;
    private Gender gender; // enum
    private Goal goal; // enum
    private ActivityLevel activityLevel; // enum

    // Constructor
    public User(int age, double height, double weight, Gender gender, Goal goal, ActivityLevel activityLevel) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.goal = goal;
        this.activityLevel = activityLevel;
    }

    // Getters
    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Gender getGender() {
        return gender;
    }

    public Goal getGoal() {
        return goal;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }
}
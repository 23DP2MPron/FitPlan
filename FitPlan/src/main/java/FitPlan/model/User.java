package FitPlan.model;

public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private double height; 
    private double weight; 
    private Gender gender;
    private ActivityLevel activityLevel;
    private Goal currentGoal;
    private MacroData macroData;
    private WorkoutPlan workoutPlan;

    public User() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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
    
    public MacroData getMacroData() {
        return macroData;
    }
    
    public void setMacroData(MacroData macroData) {
        this.macroData = macroData;
    }
    
    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }
    
    public void setWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlan = workoutPlan;
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
                ", macroData=" + macroData +
                ", workoutPlan=" + workoutPlan +
                '}';
    }
}
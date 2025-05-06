# Project Structure and Key Classes

Below is the structure of the application, divided into packages with their respective classes and responsibilities.

<pre> fitplan/
├── controller/
│   ├── AuthController.java
│   ├── MenuController.java
│   └── ConsoleRouter.java
│
├── model/
│   ├── User.java
│   ├── Goal.java
│   ├── MacroResult.java
│   ├── Workout.java
│   ├── Exercise.java
│   ├── WeightEntry.java
│   └── Measurement.java
│   
│
├── service/
│   ├── UserService.java
│   ├── MacroCalculatorService.java
│   ├── WorkoutPlanService.java
│   ├── ProgressService.java
│   └── AssistantService.java
│
├── repository/
│   ├── UserRepository.java
│   ├── WorkoutRepository.java
│   ├── ProgressRepository.java
│   └── DatabaseManager.java
│
├── view/
│   ├── WorkoutPlanRenderer.java
│   ├── ViewRenderer.java
│   └── InputHelper.java
│
└── FitPlanApp.java  # Entry point of the application </pre>

## 1. `model/` — Data and Entities
This package contains classes representing real-world objects in the application.  
*Note: These classes contain no logic — only fields, getters, and setters.*

| Class         | Purpose                                      |
|---------------|----------------------------------------------|
| `User`        | Holds name, age, height, weight, goal, etc. |
| `Workout`     | Workout model (date, exercises, type)       |
| `MacroResult` | Calculated calories, proteins, fats, carbs  |
| `Measurement` | Measurements: waist, chest, etc.            |
| `WeightEntry` | Weight + date                               |
| `Goal`        | User goal — enum: `LOSE_WEIGHT`, `GAIN_MASS`, etc. |

🔧 These are simple data containers.

## 2. `service/` — Logic and Calculations
This package holds everything that computes, generates, or analyzes data.  
*This is the "brain" of the application where all the logic resides.*

| Class                  | Purpose                                      |
|------------------------|----------------------------------------------|
| `UserService`          | Manages users (creation, authorization)     |
| `MacroCalculatorService` | Calculates macros (calories, proteins, fats, carbs) using formulas |
| `WorkoutPlanService`   | Generates workouts based on goals           |
| `ProgressService`      | Updates weight and measurements             |
| `AssistantService`     | Provides advice and motivation              |

🧠 The core logic lives here.

## 3. `controller/` — Logic Management
This acts as a "middleman" between the view (user) and services. It handles:  
- Processing user actions  
- Calling the appropriate services  
- Managing navigation  

| Class           | Purpose                                      |
|-----------------|----------------------------------------------|
| `AuthController`| Manages registration/login                  |
| `ConsoleRouter` | Handles transitions between menus           |
| `MenuController`| Controls main actions in the application    |

🎮 The controller is the conductor.

## 4. `repository/` — Database Operations
This package contains classes that save and retrieve data from the database.  

| Class             | Purpose                                      |
|-------------------|----------------------------------------------|
| `UserRepository`  | Saves, finds, updates users                 |
| `WorkoutRepository`| Same as above — for workouts               |
| `ProgressRepository`| Stores weights and measurements           |
| `DatabaseManager` | Connects to the DB, initializes tables, etc.|

💾 Repositories are the bridge between Java code and database tables.

## 5. `view/` — User Interface
This package deals with everything the user sees in the console.  

| Class          | Purpose                                      |
|----------------|----------------------------------------------|
| `WorkoutPlanRenderer`     | Displays the workout plan         |
| `ViewRenderer` | Formats and prints information nicely       |
| `InputHelper`  | Accepts and validates keyboard input        |

📺 This is the text-based user interface (console UI).

## 6. `FitPlanApp.java` — Entry Point
This is the `main()` method — the starting point of the application.  
It typically:  
- Creates necessary controllers and services  
- Opens the main menu  

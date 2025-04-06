# Key Classes in Each Module

## 📁 Authentication & Profile
- **User** – User model  
- **UserService** – Logic for registration, login, and profile editing  
- **AuthController** – Handles user interaction during login  
- **UserRepository** – Database operations for users  

## 📁 Macro Calculation
- **MacroCalculatorService** – Calculates macros (calories, proteins, fats, carbs) using formulas  
- **Goal** – Enum: `WEIGHT_LOSS`, `MAINTENANCE`, `MUSCLE_GAIN`  
- **MacroResult** – Model with calculated macro values  

## 📁 Workout Planner
- **WorkoutPlanService** – Logic for generating workout plans  
- **Workout** – Workout model (date, type, exercises, etc.)  
- **Exercise** – Name, weight, sets, etc.  
- **WorkoutRepository** – Storage and retrieval from the database  

## 📁 Progress Tracker
- **ProgressService** – Logic for tracking weight and measurements  
- **Measurement** – Model: waist, chest, etc.  
- **WeightEntry** – Date and weight  
- **ProgressRepository** – Database layer for the above  

## 📁 Smart Assistant
- **AssistantService** – Data analysis and advice generation  
- **Reminder** – Reminder models  

## 📁 Database
- **DatabaseManager** – Database connection and initialization  
- **DAO/Repository classes** – Such as `UserRepository`, `WorkoutRepository`, etc.  

## 📁 Console UI
- **MainMenu** – Main menu  
- **InputHelper** – Input processing  
- **ViewRenderer** – Formatting console output  
- **ConsoleRouter** – Navigation between sections  

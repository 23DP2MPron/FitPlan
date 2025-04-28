package FitPlan.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Measurement {
    private LocalDate date;
    // Using a map for flexibility (e.g., "Chest", "Waist", "Hips", "Bicep R")
    private Map<String, Double> measurementsCm;

    public Measurement(LocalDate date) {
        this.date = date;
        this.measurementsCm = new HashMap<>();
    }

     public Measurement(LocalDate date, Map<String, Double> measurementsCm) {
        this.date = date;
        this.measurementsCm = new HashMap<>(measurementsCm); // Copy map
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, Double> getMeasurementsCm() {
        return new HashMap<>(measurementsCm); // Return a copy
    }

    public void addMeasurement(String bodyPart, double valueCm) {
        this.measurementsCm.put(bodyPart, valueCm);
    }

    public Double getMeasurement(String bodyPart) {
        return this.measurementsCm.get(bodyPart);
    }

    @Override
    public String toString() {
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
         String measurementString = measurementsCm.entrySet().stream()
                 .map(entry -> entry.getKey() + ": " + entry.getValue() + " cm")
                 .collect(Collectors.joining(", "));
        return "Date: " + date.format(formatter) + ", Measurements: [" + measurementString + "]";
    }
}
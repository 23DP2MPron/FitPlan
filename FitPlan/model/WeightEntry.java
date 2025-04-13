package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeightEntry {
    private LocalDate date;
    private double weightKg;

    public WeightEntry(LocalDate date, double weightKg) {
        this.date = date;
        this.weightKg = weightKg;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getWeightKg() {
        return weightKg;
    }

    @Override
    public String toString() {
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD
        return "Date: " + date.format(formatter) + ", Weight: " + weightKg + " kg";
    }
}
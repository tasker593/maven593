package com.epam.lab.model.sweets;

public abstract class Sweet {

    private double sugarLevel;
    private double weight;

    public double getSugarLevel() {
        return sugarLevel;
    }

    public double getWeight() {
        return weight;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString() {
        return String.format("%s %s %s", getClass().getSimpleName(), sugarLevel, weight);
    }
}

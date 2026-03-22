package model;

public class Metric {
    private String name;
    private int coefficient;
    private String direction; // "Higher" or "Lower"
    private double rangeMin;
    private double rangeMax;
    private String unit;
    private double value;
    private double score;

    public Metric(String name, int coefficient, String direction, double rangeMin, double rangeMax, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.unit = unit;
        this.value = rangeMin; // Default value
    }

    public void calculateScore() {
        if (rangeMax == rangeMin) {
            this.score = 1.0;
            return;
        }

        if ("Higher".equalsIgnoreCase(direction)) {
            this.score = 1.0 + ((value - rangeMin) / (rangeMax - rangeMin)) * 4.0;
        } else {
            this.score = 5.0 - ((value - rangeMin) / (rangeMax - rangeMin)) * 4.0;
        }

        // clamp between 1.0 and 5.0
        if (this.score < 1.0) this.score = 1.0;
        if (this.score > 5.0) this.score = 5.0;

        // round to nearest 0.5
        this.score = Math.round(this.score * 2.0) / 2.0;
    }

    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public String getDirection() { return direction; }
    public double getRangeMin() { return rangeMin; }
    public double getRangeMax() { return rangeMax; }
    public String getUnit() { return unit; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public double getScore() { return score; }
}

package model;

import java.util.ArrayList;
import java.util.List;

public class QualityDimension {
    private String name;
    private int coefficient; // Weight for the dimension in overall analysis
    private List<Metric> metrics;

    public QualityDimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    public double calculateWeightedAverage() {
        double totalWeightedScore = 0;
        int totalCoefficient = 0;

        for (Metric m : metrics) {
            m.calculateScore();
            totalWeightedScore += m.getScore() * m.getCoefficient();
            totalCoefficient += m.getCoefficient();
        }

        if (totalCoefficient == 0) return 1.0;
        
        double avg = totalWeightedScore / totalCoefficient;
        //dimensionScore = Σ(metricScore * metricCoefficient) / Σ(metricCoefficient)
        return Math.round(avg * 2.0) / 2.0;
    }

    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public List<Metric> getMetrics() { return metrics; }
}

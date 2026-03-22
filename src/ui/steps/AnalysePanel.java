package ui.steps;

import model.DataManager;
import model.QualityDimension;
import model.Scenario;
import ui.StepPanel;
import ui.components.RadarChart;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalysePanel extends StepPanel {
    private JPanel progressPanel;
    private RadarChart radarChart;
    private JTextArea gapAnalysisArea;

    public AnalysePanel(int num, String name) {
        super(num, name);
        initComponents();
    }

    private void initComponents() {
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 20, 20));

        // weighted avgs
        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        JScrollPane leftScroll = new JScrollPane(progressPanel);
        leftScroll.setBorder(BorderFactory.createTitledBorder("Dimension-Based Weighted Averages"));
        mainContent.add(leftScroll);

        // radar chart and gap analysis
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        radarChart = new RadarChart();
        rightPanel.add(radarChart, BorderLayout.CENTER);

        gapAnalysisArea = new JTextArea();
        gapAnalysisArea.setEditable(false);
        gapAnalysisArea.setBackground(new Color(250, 250, 250));
        gapAnalysisArea.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));
        rightPanel.add(gapAnalysisArea, BorderLayout.SOUTH);

        mainContent.add(rightPanel);

        add(new JLabel("Step 5: Analyse Results", JLabel.CENTER), BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    @Override
    public void onShown() {
        Scenario scenario = DataManager.getInstance().getSelectedScenario();
        if (scenario == null) return;

        // Update Progress Bars
        progressPanel.removeAll();
        double minScore = 6.0;
        model.QualityDimension weakestDimension = null;

        for (model.QualityDimension d : scenario.getDimensions()) {
            double score = d.calculateWeightedAverage();
            if (score < minScore) {
                minScore = score;
                weakestDimension = d;
            }

            JPanel p = new JPanel(new BorderLayout());
            p.setMaximumSize(new java.awt.Dimension(400, 50));
            p.add(new JLabel(d.getName() + ": " + score), BorderLayout.NORTH);
            JProgressBar bar = new JProgressBar(0, 500); // 0.0 to 5.0
            bar.setValue((int) (score * 100));
            bar.setForeground(getColorForScore(score));
            p.add(bar, BorderLayout.CENTER);
            progressPanel.add(p);
            progressPanel.add(Box.createVerticalStrut(10));
        }
        progressPanel.revalidate();
        progressPanel.repaint();

        // Update Radar Chart
        radarChart.setDimensions(scenario.getDimensions());

        // Update Gap Analysis
        if (weakestDimension != null) {
            double gap = 5.0 - minScore;
            String level = getQualityLevel(minScore);
            StringBuilder sb = new StringBuilder();
            sb.append("Dimension: ").append(weakestDimension.getName()).append(" (Score: ").append(minScore).append(")\n");
            sb.append("Gap Value: ").append(String.format("%.1f", gap)).append("\n");
            sb.append("Quality Level: ").append(level).append("\n\n");
            sb.append("\"This dimension has the lowest score and requires the most improvement.\"");
            gapAnalysisArea.setText(sb.toString());
        }
    }

    private Color getColorForScore(double score) {
        if (score >= 4.0) return new Color(40, 167, 69); // Green
        if (score >= 3.0) return new Color(255, 193, 7); // Yellow/Amber
        return new Color(220, 53, 69); // Red
    }

    private String getQualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor";
    }

    @Override
    public boolean validateStep() { return true; }

    @Override
    public void onNext() { }
}

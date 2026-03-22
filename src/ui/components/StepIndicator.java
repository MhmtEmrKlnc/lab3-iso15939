package ui.components;

import javax.swing.*;
import java.awt.*;

public class StepIndicator extends JPanel {
    private int currentStep = 1;
    private final String[] stepNames = {"Profile", "Define", "Plan", "Collect", "Analyse"};

    public StepIndicator() {
        setPreferredSize(new Dimension(800, 60));
        setBackground(new Color(245, 245, 245));
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int n = stepNames.length;
        int w = getWidth();
        int margin = 50;
        int stepW = (w - 2 * margin) / (n - 1);

        for (int i = 0; i < n; i++) {
            int x = margin + i * stepW;
            int y = 30;

            // line between steps
            if (i < n - 1) {
                g2.setColor(i + 2 <= currentStep ? new Color(0, 120, 215) : Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(x + 15, y, x + stepW - 15, y);
            }

            // circle
            if (i + 1 < currentStep) {
                g2.setColor(new Color(40, 167, 69)); // Success green
                g2.fillOval(x - 15, y - 15, 30, 30);
                g2.setColor(Color.WHITE);
                g2.drawString("✓", x - 5, y + 5);
            } else if (i + 1 == currentStep) {
                g2.setColor(new Color(0, 120, 215)); // Active blue
                g2.fillOval(x - 15, y - 15, 30, 30);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(String.valueOf(i + 1), x - 5, y + 5);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillOval(x - 15, y - 15, 30, 30);
                g2.setColor(Color.WHITE);
                g2.drawString(String.valueOf(i + 1), x - 5, y + 5);
            }

            g2.setColor(i + 1 == currentStep ? Color.BLACK : Color.GRAY);
            g2.setFont(new Font("Arial", i + 1 == currentStep ? Font.BOLD : Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(stepNames[i], x - fm.stringWidth(stepNames[i]) / 2, y + 30);
        }
    }
}

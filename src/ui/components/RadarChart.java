package ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadarChart extends JPanel {
    private List<model.QualityDimension> dimensions;

    public RadarChart() {
        setPreferredSize(new java.awt.Dimension(300, 300));
        setBackground(Color.WHITE);
    }

    public void setDimensions(List<model.QualityDimension> dimensions) {
        this.dimensions = dimensions;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dimensions == null || dimensions.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 40;

        int numPoints = dimensions.size();
        double angleStep = 2 * Math.PI / numPoints;

        // Draw background circles/polygons
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= 5; i++) {
            int r = radius * i / 5;
            int[] px = new int[numPoints];
            int[] py = new int[numPoints];
            for (int j = 0; j < numPoints; j++) {
                px[j] = centerX + (int) (r * Math.cos(j * angleStep - Math.PI / 2));
                py[j] = centerY + (int) (r * Math.sin(j * angleStep - Math.PI / 2));
            }
            g2.drawPolygon(px, py, numPoints);
        }

        // Draw axes and labels
        g2.setColor(Color.BLACK);
        for (int j = 0; j < numPoints; j++) {
            int x = centerX + (int) (radius * Math.cos(j * angleStep - Math.PI / 2));
            int y = centerY + (int) (radius * Math.sin(j * angleStep - Math.PI / 2));
            g2.drawLine(centerX, centerY, x, y);
            
            String label = dimensions.get(j).getName();
            int lx = centerX + (int) ((radius + 15) * Math.cos(j * angleStep - Math.PI / 2));
            int ly = centerY + (int) ((radius + 15) * Math.sin(j * angleStep - Math.PI / 2));
            g2.drawString(label, lx - 20, ly);
        }

        // Draw value polygon
        int[] vx = new int[numPoints];
        int[] vy = new int[numPoints];
        for (int j = 0; j < numPoints; j++) {
            double score = dimensions.get(j).calculateWeightedAverage();
            int r = (int) (radius * score / 5.0);
            vx[j] = centerX + (int) (r * Math.cos(j * angleStep - Math.PI / 2));
            vy[j] = centerY + (int) (r * Math.sin(j * angleStep - Math.PI / 2));
        }
        g2.setColor(new Color(0, 120, 215, 100)); // Semi-transparent blue
        g2.fillPolygon(vx, vy, numPoints);
        g2.setColor(new Color(0, 120, 215));
        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(vx, vy, numPoints);
    }
}

package ui.steps;

import model.DataManager;
import model.QualityDimension;
import model.Metric;
import model.Scenario;
import ui.StepPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends StepPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public PlanPanel(int num, String name) {
        super(num, name);
        initComponents();
    }

    private void initComponents() {
        String[] columns = {"Dimension", "Metric", "Coeff.", "Direction", "Range", "Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(new JLabel("Step 3: Plan Measurement (Read-only Dataset)", JLabel.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void onShown() {
        Scenario scenario = DataManager.getInstance().getSelectedScenario();
        tableModel.setRowCount(0);
        if (scenario != null) {
            for (QualityDimension d : scenario.getDimensions()) {
                for (Metric m : d.getMetrics()) {
                    tableModel.addRow(new Object[]{
                        d.getName() + " (" + d.getCoefficient() + ")",
                        m.getName(),
                        m.getCoefficient(),
                        m.getDirection(),
                        m.getRangeMin() + " - " + m.getRangeMax(),
                        m.getUnit()
                    });
                }
            }
        }
    }

    @Override
    public boolean validateStep() { return true; }

    @Override
    public void onNext() { }
}

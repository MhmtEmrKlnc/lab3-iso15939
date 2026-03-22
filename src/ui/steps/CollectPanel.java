package ui.steps;

import model.DataManager;
import model.QualityDimension;
import model.Metric;
import ui.StepPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollectPanel extends StepPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Metric> flatMetrics;

    public CollectPanel(int num, String name) {
        super(num, name);
        initComponents();
    }

    private void initComponents() {
        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only "Value" column is editable
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 3) {
                int row = e.getFirstRow();
                try {
                    String valStr = tableModel.getValueAt(row, 3).toString();
                    double val = Double.parseDouble(valStr);
                    Metric m = flatMetrics.get(row);
                    m.setValue(val);
                    m.calculateScore();
                    tableModel.setValueAt(m.getScore(), row, 4);
                } catch (Exception ex) {

                }
            }
        });

        add(new JLabel("Step 4: Collect Data (Enter Raw Values)", JLabel.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void onShown() {
        tableModel.setRowCount(0);
        flatMetrics = new ArrayList<>();
        if (DataManager.getInstance().getSelectedScenario() != null) {
            for (QualityDimension d : DataManager.getInstance().getSelectedScenario().getDimensions()) {
                for (Metric m : d.getMetrics()) {
                    flatMetrics.add(m);
                    tableModel.addRow(new Object[]{
                        m.getName(),
                        m.getDirection(),
                        m.getRangeMin() + " - " + m.getRangeMax(),
                        m.getValue(),
                        m.getScore(),
                        m.getCoefficient() + " / " + m.getUnit()
                    });
                }
            }
        }
    }

    @Override
    public boolean validateStep() {
        for (Metric m : flatMetrics) {
            if (m.getValue() < m.getRangeMin() || m.getValue() > m.getRangeMax()) {
                JOptionPane.showMessageDialog(this, "Value for " + m.getName() + " is out of range.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onNext() { }
}

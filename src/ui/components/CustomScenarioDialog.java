package ui.components;

import model.DataManager;
import model.Metric;
import model.QualityDimension;
import model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomScenarioDialog extends JDialog {
    private JTextField nameField;
    private JPanel dimensionsPanel;
    private List<DimensionEntry> dimensionEntries;
    private boolean saved = false;

    public CustomScenarioDialog(Frame owner) {
        super(owner, "Create Custom Scenario", true);
        dimensionEntries = new ArrayList<>();
        initComponents();
        setSize(500, 600);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Scenario Name:"));
        nameField = new JTextField(20);
        topPanel.add(nameField);
        add(topPanel, BorderLayout.NORTH);

        dimensionsPanel = new JPanel();
        dimensionsPanel.setLayout(new BoxLayout(dimensionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(dimensionsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addDimBtn = new JButton("Add Dimension");
        addDimBtn.addActionListener(e -> addDimensionEntry());
        
        JButton saveBtn = new JButton("Save Scenario");
        saveBtn.addActionListener(e -> save());
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        bottomPanel.add(addDimBtn);
        bottomPanel.add(saveBtn);
        bottomPanel.add(cancelBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        addDimensionEntry();
    }

    private void addDimensionEntry() {
        DimensionEntry entry = new DimensionEntry();
        dimensionEntries.add(entry);
        dimensionsPanel.add(entry);
        dimensionsPanel.revalidate();
    }

    private void save() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a scenario name.");
            return;
        }

        Scenario s = new Scenario(nameField.getText() + " (Custom)");
        for (DimensionEntry de : dimensionEntries) {
            QualityDimension qd = new QualityDimension(de.getName(), 50);
            qd.addMetric(new Metric(de.getMetricName(), 100, de.getDirection(), 0, 100, "units"));
            s.addDimension(qd);
        }

        DataManager.getInstance().addCustomScenario(s);
        saved = true;
        dispose();
    }

    public boolean isSaved() { return saved; }

    private class DimensionEntry extends JPanel {
        private JTextField dimName;
        private JTextField metricName;
        private JComboBox<String> directionCombo;

        public DimensionEntry() {
            setBorder(BorderFactory.createTitledBorder("Dimension & Metric"));
            setLayout(new GridLayout(3, 2, 5, 5));
            add(new JLabel("Dimension Name:"));
            dimName = new JTextField();
            add(dimName);
            
            add(new JLabel("Metric Name:"));
            metricName = new JTextField();
            add(metricName);

            add(new JLabel("Direction:"));
            directionCombo = new JComboBox<>(new String[]{"Higher", "Lower"});
            add(directionCombo);
            
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        }

        public String getName() { return dimName.getText(); }
        public String getMetricName() { return metricName.getText(); }
        public String getDirection() { return (String) directionCombo.getSelectedItem(); }
    }
}

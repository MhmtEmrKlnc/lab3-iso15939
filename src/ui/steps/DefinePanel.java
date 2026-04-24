package ui.steps;

import model.DataManager;
import model.Scenario;
import ui.StepPanel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DefinePanel extends StepPanel {
    private JRadioButton productQualityRb;
    private JRadioButton processQualityRb;
    private JComboBox<String> modeCombo;
    private JComboBox<Scenario> scenarioCombo;

    public DefinePanel(int num, String name) {
        super(num, name);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Step 2: Define Quality Dimensions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Quality Type:"), gbc);
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        productQualityRb = new JRadioButton("Product Quality", true);
        processQualityRb = new JRadioButton("Process Quality");
        ButtonGroup bg = new ButtonGroup();
        bg.add(productQualityRb);
        bg.add(processQualityRb);
        
        java.awt.event.ActionListener typeListener = e -> updateScenarios();
        productQualityRb.addActionListener(typeListener);
        processQualityRb.addActionListener(typeListener);
        
        typePanel.add(productQualityRb);
        typePanel.add(processQualityRb);
        gbc.gridx = 1; add(typePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Mode Selection:"), gbc);
        modeCombo = new JComboBox<>(new String[]{"Custom (Bonus)", "Health", "Education"});
        gbc.gridx = 1; add(modeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        
        scenarioCombo = new JComboBox<>();
        scenarioCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Scenario) setText(((Scenario) value).getName());
                return this;
            }
        });

        JButton addCustomBtn = new JButton("Add New Scenario (Custom)");
        addCustomBtn.setVisible(false);
        addCustomBtn.addActionListener(e -> {
            ui.components.CustomScenarioDialog dialog = new ui.components.CustomScenarioDialog((Frame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                updateScenarios();
            }
        });
        add(addCustomBtn, gbc);
        
        modeCombo.addActionListener(e -> {
            updateScenarios();
            addCustomBtn.setVisible("Custom (Bonus)".equals(modeCombo.getSelectedItem()));
            revalidate();
            repaint();
        });
        
        // deafult -> education
        modeCombo.setSelectedItem("Education");
        updateScenarios();
        addCustomBtn.setVisible(false);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Scenario selection:"), gbc);
        gbc.gridx = 1; add(scenarioCombo, gbc);
    }

    private void updateScenarios() {
        String mode = (String) modeCombo.getSelectedItem();
        String qualityType = productQualityRb.isSelected() ? "Product Quality" : "Process Quality";
        scenarioCombo.removeAllItems();
        List<Scenario> scenarios = DataManager.getInstance().getFilteredScenarios(mode, qualityType);
        if (scenarios != null) {
            for (Scenario s : scenarios) {
                scenarioCombo.addItem(s);
            }
        }
    }

    @Override
    public boolean validateStep() {
        if (scenarioCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a scenario.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onShown() {
        // Optional: restore state
    }

    @Override
    public void onNext() {
        DataManager.getInstance().setSelectedQualityType(productQualityRb.isSelected() ? "Product Quality" : "Process Quality");
        DataManager.getInstance().setSelectedMode((String) modeCombo.getSelectedItem());
        DataManager.getInstance().setSelectedScenario((Scenario) scenarioCombo.getSelectedItem());
    }
}

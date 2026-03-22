package ui.steps;

import model.DataManager;
import ui.StepPanel;
import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends StepPanel {
    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionField;

    public ProfilePanel(int num, String name) {
        super(num, name);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Step 1: Profile Information", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1; add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("School:"), gbc);
        schoolField = new JTextField(20);
        gbc.gridx = 1; add(schoolField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Session Name:"), gbc);
        sessionField = new JTextField(20);
        gbc.gridx = 1; add(sessionField, gbc);
    }

    @Override
    public boolean validateStep() {
        if (usernameField.getText().trim().isEmpty() || 
            schoolField.getText().trim().isEmpty() || 
            sessionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields before proceeding.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onShown() {
        // Load from data manager if already filled
        usernameField.setText(DataManager.getInstance().getProfile().getUsername());
        schoolField.setText(DataManager.getInstance().getProfile().getSchool());
        sessionField.setText(DataManager.getInstance().getProfile().getSessionName());
    }

    @Override
    public void onNext() {
        DataManager.getInstance().getProfile().setUsername(usernameField.getText());
        DataManager.getInstance().getProfile().setSchool(schoolField.getText());
        DataManager.getInstance().getProfile().setSessionName(sessionField.getText());
    }
}

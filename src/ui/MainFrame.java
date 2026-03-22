package ui;

import model.DataManager;
import ui.components.StepIndicator;
import ui.steps.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StepIndicator stepIndicator;
    private JButton nextButton;
    private JButton backButton;
    private List<StepPanel> steps;
    private int currentStep = 0;

    public MainFrame() {
        setTitle("ISO 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        stepIndicator = new StepIndicator();
        add(stepIndicator, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        steps = new ArrayList<>();

        initializeSteps();

        for (StepPanel step : steps) {
            cardPanel.add(step, step.getStepName());
        }
        add(cardPanel, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        nextButton = new JButton("Next");

        backButton.setEnabled(false);
        backButton.addActionListener(e -> previousStep());
        nextButton.addActionListener(e -> handleNext());

        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private void initializeSteps() {
        steps.add(new ProfilePanel(1, "Profile"));
        steps.add(new DefinePanel(2, "Define"));
        steps.add(new PlanPanel(3, "Plan"));
        steps.add(new CollectPanel(4, "Collect"));
        steps.add(new AnalysePanel(5, "Analyse"));
    }

    private void handleNext() {
        if (currentStep < steps.size()) {
            StepPanel current = steps.get(currentStep);
            if (current.validateStep()) {
                current.onNext();
                if (currentStep < steps.size() - 1) {
                    currentStep++;
                    updateView();
                } else {
                    JOptionPane.showMessageDialog(this, "Simulation Complete! Returning to profile screen.");
                    currentStep = 0;
                    updateView();
                }
            }
        }
    }

    private void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateView();
        }
    }

    private void updateView() {
        cardLayout.show(cardPanel, steps.get(currentStep).getStepName());
        stepIndicator.setCurrentStep(currentStep + 1);
        backButton.setEnabled(currentStep > 0);
        nextButton.setText(currentStep == steps.size() - 1 ? "Finish" : "Next");
        steps.get(currentStep).onShown();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

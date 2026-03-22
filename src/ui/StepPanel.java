package ui;

import javax.swing.*;
import java.awt.*;

public abstract class StepPanel extends JPanel {
    protected int stepNumber;
    protected String stepName;

    public StepPanel(int stepNumber, String stepName) {
        this.stepNumber = stepNumber;
        this.stepName = stepName;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public abstract boolean validateStep();
    public abstract void onShown();
    public abstract void onNext();
    
    public String getStepName() { return stepName; }
}

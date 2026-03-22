package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static DataManager instance;
    private Profile profile;
    private String selectedQualityType;
    private String selectedMode;
    private Scenario selectedScenario;
    private Map<String, List<Scenario>> scenariosByMode;

    private DataManager() {
        profile = new Profile();
        scenariosByMode = new HashMap<>();
        initializeData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void initializeData() {
        scenariosByMode = new HashMap<>();
        
        // Mode: Education
        List<Scenario> educationScenarios = new ArrayList<>();
        
        // Scenario C (Education - Product Quality)
        Scenario scenarioC = new Scenario("Scenario C - Team Alpha (Product)");
        addEducationProductMetrics(scenarioC);
        educationScenarios.add(scenarioC);

        // Scenario D (Education - Process Quality)
        Scenario scenarioD = new Scenario("Scenario D - Team Beta (Process)");
        addEducationProcessMetrics(scenarioD);
        educationScenarios.add(scenarioD);
        
        scenariosByMode.put("Education", educationScenarios);

        // Mode: Health
        List<Scenario> healthScenarios = new ArrayList<>();
        
        // Scenario A (Health - Product Quality)
        Scenario healthA = new Scenario("Scenario A - Hospital X (Product)");
        addHealthProductMetrics(healthA);
        healthScenarios.add(healthA);

        // Scenario B (Health - Process Quality)
        Scenario healthB = new Scenario("Scenario B - Clinic Y (Process)");
        addHealthProcessMetrics(healthB);
        healthScenarios.add(healthB);
        
        scenariosByMode.put("Health", healthScenarios);
        scenariosByMode.put("Custom (Bonus)", new ArrayList<>());
    }

    public List<Scenario> getFilteredScenarios(String mode, String qualityType) {
        List<Scenario> all = scenariosByMode.get(mode);
        if (all == null) return new ArrayList<>();
        
        List<Scenario> filtered = new ArrayList<>();
        for (Scenario s : all) {
            if (s.getName().contains("(" + qualityType.split(" ")[0] + ")")) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    private void addEducationProductMetrics(Scenario s) {
        QualityDimension usability = new QualityDimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher", 0, 100, "points"));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower", 0, 60, "min"));
        
        QualityDimension perfEfficiency = new QualityDimension("Perf. Efficiency", 20);
        perfEfficiency.addMetric(new Metric("Video start time", 50, "Lower", 0, 15, "sec"));
        perfEfficiency.addMetric(new Metric("Concurrent exams", 50, "Higher", 0, 600, "users"));
        
        QualityDimension accessibility = new QualityDimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, "Higher", 0, 100, "%"));
        accessibility.addMetric(new Metric("Screen reader score", 50, "Higher", 0, 100, "%"));
        
        QualityDimension reliability = new QualityDimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, "Higher", 95, 100, "%"));
        reliability.addMetric(new Metric("MTTR", 50, "Lower", 0, 120, "min"));
        
        QualityDimension funcSuitability = new QualityDimension("Func. Suitability", 15);
        funcSuitability.addMetric(new Metric("Feature completion", 50, "Higher", 0, 100, "%"));
        funcSuitability.addMetric(new Metric("Assignment submit rate", 50, "Higher", 0, 100, "%"));

        s.addDimension(usability);
        s.addDimension(perfEfficiency);
        s.addDimension(accessibility);
        s.addDimension(reliability);
        s.addDimension(funcSuitability);
    }

    private void addEducationProcessMetrics(Scenario s) {
        QualityDimension devProcess = new QualityDimension("Development Process", 60);
        devProcess.addMetric(new Metric("Sprint Velocity", 50, "Higher", 0, 50, "sp"));
        devProcess.addMetric(new Metric("Code Review Time", 50, "Lower", 0, 48, "hrs"));
        
        QualityDimension teamCollab = new QualityDimension("Team Collaboration", 40);
        teamCollab.addMetric(new Metric("Daily Meeting Rate", 100, "Higher", 0, 100, "%"));

        s.addDimension(devProcess);
        s.addDimension(teamCollab);
    }

    private void addHealthProductMetrics(Scenario s) {
        QualityDimension safety = new QualityDimension("Patient Safety", 60);
        safety.addMetric(new Metric("Dosage Error Rate", 50, "Lower", 0, 1.0, "%"));
        safety.addMetric(new Metric("Critical Alerts Response", 50, "Lower", 0, 30, "min"));
        
        QualityDimension security = new QualityDimension("Data Security", 40);
        security.addMetric(new Metric("Unauthorized Access Attempts", 100, "Lower", 0, 10, "count"));

        s.addDimension(safety);
        s.addDimension(security);
    }

    private void addHealthProcessMetrics(Scenario s) {
        QualityDimension flow = new QualityDimension("Clinical Flow", 60);
        flow.addMetric(new Metric("Patient Throughput", 50, "Higher", 0, 20, "patients/hr"));
        flow.addMetric(new Metric("Average Wait Time", 50, "Lower", 0, 120, "min"));
        
        QualityDimension efficiency = new QualityDimension("Resource Efficiency", 40);
        efficiency.addMetric(new Metric("Bed Occupancy Rate", 100, "Higher", 0, 100, "%"));

        s.addDimension(flow);
        s.addDimension(efficiency);
    }

    public Profile getProfile() { return profile; }
    public String getSelectedQualityType() { return selectedQualityType; }
    public void setSelectedQualityType(String type) { this.selectedQualityType = type; }
    public String getSelectedMode() { return selectedMode; }
    public void setSelectedMode(String mode) { this.selectedMode = mode; }
    public Scenario getSelectedScenario() { return selectedScenario; }
    public void setSelectedScenario(Scenario scenario) { this.selectedScenario = scenario; }
    public List<Scenario> getScenariosForMode(String mode) { return scenariosByMode.get(mode); }
}

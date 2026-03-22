package model;

public class Profile {
    private String username;
    private String school;
    private String sessionName;

    public Profile() {
        this.username = "";
        this.school = "";
        this.sessionName = "";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }

    public boolean isValid() {
        return !username.trim().isEmpty() && !school.trim().isEmpty() && !sessionName.trim().isEmpty();
    }
}

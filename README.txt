ISO 15939 Measurement Process Simulator
=======================================

Name: Mehmet Emre Kılınç
Student ID: 202111058

SENG272 - hw3 - ISO 15939 Measurement Process Simulator

Compilation and Run Instructions
--------------------------------
The project is built using standard Java SE 17+ and does not require any external libraries.

1. Open a terminal/command prompt in the project root directory.
2. Compile the project:
   javac -d bin -sourcepath src src/App.java src/model/*.java src/ui/*.java src/ui/steps/*.java src/ui/components/*.java

3. Run the application:
   java -cp bin App

Application Overview
--------------------
This simulator follows the 5 core steps of the ISO/IEC 15939 measurement process:
1. Profile: Enter user and session information.
2. Define: Select quality type (Product/Process), mode, and scenario.
3. Plan: View dimensions and metrics belonging to the selected scenario.
4. Collect: Enter raw data values and see automatically calculated scores (1-5).
5. Analyse: View dimension-based weighted averages, a radar chart, and gap analysis.


- MVC Pattern: Separation of concerns between Model (data/logic) and UI classes.
- Radar chart implemented using Graphics2D.

### Screenshots

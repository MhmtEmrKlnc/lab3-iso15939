# ISO 15939 Measurement Process Simulator
=======================================

- Name: <b>Mehmet Emre Kılınç</b>
- Student ID: <b>202111058</b>

SENG272 - hw3 - ISO 15939 Measurement Process

Requirements and Design Document: https://github.com/MhmtEmrKlnc/lab3-iso15939/blob/main/Seng272_RequirementsAndDesign_MehmetEmreKilinc.docx

## Compilation and Run Instructions
--------------------------------
The project is built using standard Java SE 17+ and does not require any external libraries.

1. Open a terminal/command prompt in the project root directory.
2. Compile the project:
   javac -d bin -sourcepath src src/App.java src/model/*.java src/ui/*.java src/ui/steps/*.java src/ui/components/*.java

3. Run the application:
   java -cp bin App

## Application Overview
--------------------
This simulator follows the 5 core steps of the ISO/IEC 15939 measurement process:
1. Profile: Enter user and session information.
2. Define: Select quality type (Product/Process), mode, and scenario.
3. Plan: View dimensions and metrics belonging to the selected scenario.
4. Collect: Enter raw data values and see automatically calculated scores (1-5).
5. Analyse: View dimension-based weighted averages, a radar chart, and gap analysis.


- MVC Pattern: Separation of concerns between Model (data/logic) and UI classes.
- Radar chart implemented using Graphics2D.

## Screenshots
Step 1: Profile Information
<img width="988" height="695" alt="1" src="https://github.com/user-attachments/assets/20877011-6395-4ff5-8918-cb2cd9210ae7" />

Step 2: Define Quality Dimensions (Product Quality)
<img width="988" height="694" alt="2" src="https://github.com/user-attachments/assets/e3d1f94f-19bd-4371-b49d-445559d27470" />

Step 2: Define Quality Dimensions (Process Quality)
<img width="989" height="693" alt="3" src="https://github.com/user-attachments/assets/03bb02b2-8c1e-4d85-8f99-6498c9ec337b" />

Step 3: Plan Measurement
<img width="988" height="688" alt="4" src="https://github.com/user-attachments/assets/8588a43d-3459-4660-8cf0-72ab853813e8" />

Step 4: Collect Data (After Data Entered in the Value Column)
<img width="987" height="695" alt="5" src="https://github.com/user-attachments/assets/4b31b4eb-1341-4d8a-b82e-b2079788c581" />

Step 4: Validation Error
<img width="989" height="695" alt="6" src="https://github.com/user-attachments/assets/1b1b43ff-80b7-4a7e-bd7e-fc5374dd212e" />

Step 5: Analyse Results
<img width="987" height="692" alt="7" src="https://github.com/user-attachments/assets/5c9da6dc-cb82-4db4-828a-1fb38a8d1f37" />



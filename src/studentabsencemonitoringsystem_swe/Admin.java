package studentabsencemonitoringsystem_swe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Admin extends User {

    //constructor-----------------------------------------------
    public Admin(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }
    //----------------------------------------------------------

    public static void registerAbsence(Absence absence, Student student) throws IOException {

        StudentDBManagement.insertDate(absence, student);
    }

    //----------------------------------------------------------------------------------------------------
    public static void evaluateExcuse(String studentID, String date, String evaluation) {
        //find absence object associated with the id given by admin
        Excuse excuse = StudentDBManagement.getExcuse(studentID, date);

        if (excuse != null) {

            //Display reason for absence and current status of the excuse
            displayInfo();

            //store evaluation in the file
            evaluation(evaluation, studentID, date, excuse);

            //store evaluation in database
            int id = Integer.parseInt(studentID);
            StudentDBManagement.insertStatus( id,  date,  evaluation);

        } else {
            JOptionPane.showMessageDialog(null, "absence not found ");
            System.out.println("absence not found ");
        }
    }

    //----------------------------------------------------------------------------------------------------
    private static void displayInfo() {//needs modification
        try (BufferedReader reader = new BufferedReader(new FileReader("studentInfo.txt"))) {
            
            StringBuilder message = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String studentID = parts[0].substring(parts[0].indexOf(":") + 2);
                String date = parts[1].substring(parts[1].indexOf(":") + 2);
                String evaluation = parts[2].substring(parts[2].indexOf(":") + 2);

                message.append("Student ID: ").append(studentID)
                        .append("\nDate: ").append(date)
                        .append("\nCurrent status: ").append(evaluation)
                        .append("\n\n");
            }
            // Display message
            JOptionPane.showMessageDialog(null, message.toString());
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------
    private static void evaluation(String evaluation, String studentID, String date, Excuse excuse) {
        try(BufferedReader reader = new BufferedReader(new FileReader("studentInfo.txt"));
            FileWriter writer = new FileWriter("studentInfo.txt");) {

            // Convert studentID to string
            String studentIDString = String.valueOf(studentID);

            // Read the contents of the file
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean found = false;

            // Process each line in the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String currentStudentID = parts[0].substring(parts[0].indexOf(":") + 2);
                String currentDate = parts[1].substring(parts[1].indexOf(":") + 2);
                String currentStatus = parts[2].substring(parts[2].indexOf(":") + 2);

                // Check if the current line corresponds to the excuse being evaluated
                if (currentStudentID.equals(studentIDString) && currentDate.equals(date)) {
                    found = true;

                    if (evaluation.equalsIgnoreCase("accept") || evaluation.equalsIgnoreCase("reject")) {
                        // Update the status only if it's waiting for evaluation
                        if (!currentStatus.equals(evaluation)) {
                            // Update the status in the file content
                            line = "Student ID: " + studentIDString + ", Date: " + date + ", Status: " + evaluation;
                            JOptionPane.showMessageDialog(null, "Excuse status updated to " + evaluation + ".");
                        } else {
                            JOptionPane.showMessageDialog(null, "Excuse already evaluated.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input. Excuse status remains unchanged.");
                    }
                }
                // Append the current line to the file content
                fileContent.append(line).append("\n");
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "Excuse not found in the file.");
                return;
            }

            // Write the modified contents back to the file
            writer.write(fileContent.toString());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while updating the excuse status in the file.");
            e.printStackTrace();
        }
    }

}

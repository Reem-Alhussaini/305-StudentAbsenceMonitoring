package studentabsencemonitoringsystem_swe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends User {

    //constructor-----------------------------------------------
    public Admin(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }
    //----------------------------------------------------------

    public static void registerAbsence(Absence absence, Student student) throws IOException {
        //only insert date, no adding new students because of login
        //StudentDBManagement.insertStudent(student);
        StudentDBManagement.insertDate(absence, student);
    }

    //----------------------------------------------------------------------------------------------------
    public static void evaluateExcuse(String studentID, String date, Scanner scanner) { //needs modification
        // Step 1: find absence object associated with the id given by admin
        Excuse excuse = StudentDBManagement.getExcuse(studentID, date);

        if (excuse != null) {

            // Step 2: Display reason for absence and current status of the excuse
            displayInfo();

            evaluation(scanner, studentID, date, excuse);

        } else {
            System.out.println("absence not found ");
        }
    }

    //----------------------------------------------------------------------------------------------------
    private static void displayInfo() {//needs modification
        try (BufferedReader reader = new BufferedReader(new FileReader("studentInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String studentID = parts[0].substring(parts[0].indexOf(":") + 2);
                String date = parts[1].substring(parts[1].indexOf(":") + 2);
                String evaluation = parts[2].substring(parts[2].indexOf(":") + 2);

                System.out.println("Student ID: " + studentID);
                System.out.println("Date: " + date);
                System.out.println("Current status: " + evaluation);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------
    private static void evaluation(Scanner scanner, String studentID, String date, Excuse excuse) {
        try {
            // Convert studentID to string
            String studentIDString = String.valueOf(studentID);

            // Read the contents of the file
            File file = new File("studentInfo.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
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
                    // Display the reason for absence
                    System.out.println("Reason for absence: " + excuse.getReason());

                    // Prompt admin to accept or reject the excuse
                    System.out.print("Evaluate excuse (accept/reject): ");
                    String newStatus = scanner.next();
                    if (newStatus.equalsIgnoreCase("accept") || newStatus.equalsIgnoreCase("reject")) {
                        // Update the status only if it changes
                        if (!currentStatus.equals(newStatus)) {
                            // Update the status in the file content
                            line = "Student ID: " + studentIDString + ", Date: " + date + ", Status: " + newStatus;
                            System.out.println("Excuse status updated to " + newStatus + ".");
                        } else {
                            System.out.println("Excuse status remains unchanged.");
                        }
                    } else {
                        System.out.println("Invalid input. Excuse status remains unchanged.");
                    }
                }
                // Append the current line to the file content
                fileContent.append(line).append("\n");
            }
            reader.close();

            if (!found) {
                System.out.println("Excuse not found in the file.");
                return;
            }

            // Write the modified contents back to the file
            FileWriter writer = new FileWriter(file);
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the excuse status in the file.");
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------
    public static String getStudentID(Scanner scanner) {
        System.out.println("Enter the id of the student who's excuse you want to evaluate: ");
        return scanner.next();
    }

    //----------------------------------------------------------------------------------------------------
    public static String getDate(Scanner scanner) {
        System.out.println("Enter the date of the absences you want to evaluate in this format \"yyyy-mm-dd\": ");
        return scanner.next();
    }

}

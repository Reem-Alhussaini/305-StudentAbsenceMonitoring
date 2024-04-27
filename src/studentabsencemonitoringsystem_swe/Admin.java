package studentabsencemonitoringsystem_swe;

import java.io.BufferedReader;
import java.io.FileReader;
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
        System.out.print("Evaluate excuse (accepted/rejected): ");
        String newStatus = scanner.next();
        int id = Integer.parseInt(studentID);
        StudentDBManagement.insertStatus(id, date, newStatus);
        excuse.setStatus(newStatus);
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

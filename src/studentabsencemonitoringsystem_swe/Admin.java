package studentabsencemonitoringsystem_swe;

import java.io.IOException;
import java.util.Scanner;

public class Admin extends User {

    //constructor-----------------------------------------------
    public Admin(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }
    //----------------------------------------------------------

    public static void registerAbsence(Absence absence, Student student) throws IOException {
        StudentDBManagement.insertStudent(student);
        StudentDBManagement.insertDate(absence, student);
    }
    //----------------------------------------------------------------------------------------------------
    public static void evaluateExcuse(String studentID, String date, Scanner scanner) { //needs modification
        // Step 1: find absence object associated with the id given by admin
        Excuse excuse = StudentDBManagement.getExcuse(studentID, date);

        if(excuse != null){

            // Step 2: Display reason for absence and current status of the excuse
            String evaluation = displayInfo(excuse);

            // Step 3: Prompt admin to enter the evaluation of the excuse
            // Step 4: Update excuse status
            if(evaluation.equals("waiting for evaluation")) {
                evaluation(scanner, studentID, date, excuse);
            }
            else {
                System.out.println("The excuse for the student with the ID " + studentID + " was already evaluated");
            }
        }
        else {
            System.out.println("absence not found ");
        }
    }
    //----------------------------------------------------------------------------------------------------
    private static String displayInfo(Excuse excuse){//needs modification
        String cause_of_absence = excuse.getReason();
        String evaluation = excuse.getStatus();

        System.out.println("\nReason for absence: " + cause_of_absence);
        System.out.println("Current status: " + evaluation);
        return evaluation;
    }
    //----------------------------------------------------------------------------------------------------
    private static void evaluation(Scanner scanner, String studentID, String date, Excuse excuse){
        System.out.print("Evaluate excuse (accepted/rejected): ");
        String newStatus = scanner.next();
        int id = Integer.parseInt(studentID);
        StudentDBManagement.insertStatus(id, date, newStatus);
        excuse.setStatus(newStatus);
    }
    //----------------------------------------------------------------------------------------------------
    public static String getStudentID(Scanner scanner){
        System.out.println("Enter the id of the student who's excuse you want to evaluate: ");
        return scanner.next();
    }
    //----------------------------------------------------------------------------------------------------
    public static String getDate(Scanner scanner) {
        System.out.println("Enter the date of the absences you want to evaluate in this format \"yyyy-mm-dd\": ");
        return scanner.next();
    }
}

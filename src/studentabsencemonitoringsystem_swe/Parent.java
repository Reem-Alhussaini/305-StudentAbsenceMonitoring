package studentabsencemonitoringsystem_swe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Parent extends User {

    //constructor-----------------------------------------------
    public Parent(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }
    //----------------------------------------------------------
    public static void submitExcuse(String studentID, String date, String reason) { //COMPLETED

        Absence absence = StudentDBManagement.getAbsenceForParent(studentID, date);

            if (absence != null) {
                String status = StudentDBManagement.insertExcuse(absence, reason);
                Excuse excuse = new Excuse(reason, status);
                absence.setExcuse(excuse);
                // Write excuse information to file
            writeExcuseToFile(studentID, date, reason, status);
            }else{
                System.out.println("You may have entered a wrong date or ID, try again");
                StudentAbsenceMonitoringSystem.parentFunctions();
            }

    }
    //---------------------------------------------------------------------------------------------------
    public static void viewExcuseStatus(String studentID, String date) {
        Excuse excuse = StudentDBManagement.getExcuse(studentID, date);

        if (excuse != null)
            System.out.println("The excuse status is: " + excuse.getStatus());
        else
            System.out.println("absence was not found");
    }
    //---------------------------------------------------------------------------------------------------
    public static String getStudentID(Scanner scanner, int parentChoice) {
        if(parentChoice == 1) {
            System.out.println("Enter the ID of the student you want to submit an excuse for: ");
            return scanner.next();
        }else if (parentChoice == 2){
            System.out.println("Enter the ID of the student you want to view the excuse for: ");
            return scanner.next();
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------------
    public static String getDate(Scanner scanner){
        System.out.println("Enter the date of absence in this format \"yyyy-mm-dd\": ");
        return scanner.next();
    }
    //---------------------------------------------------------------------------------------------------
    public static String getReason(Scanner scanner){
        System.out.println("Enter absence reason: ");
        return scanner.next();
    }
     private static void writeExcuseToFile(String studentID, String date, String reason, String status) {
        try {
            File file = new File("studentInfo.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter writer = new FileWriter(file, true); // true for append mode
            writer.write("Student ID: " + studentID + ", Date: " + date + ", Reason: " + reason + ", Status: " + status + "\n");
            writer.close();
            System.out.println("Excuse submitted successfully and saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the excuse to file.");
            e.printStackTrace();
        }
    }
}

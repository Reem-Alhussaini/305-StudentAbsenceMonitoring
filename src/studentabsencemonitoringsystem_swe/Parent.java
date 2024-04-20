package studentabsencemonitoringsystem_swe;

import java.util.Scanner;

public class Parent extends User {

    //constructor-----------------------------------------------
    public Parent(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }
    //----------------------------------------------------------
    public static void submitExcuse(String studentID, String date, String reason) { //COMPLETED

        Absence absence = StudentDBManagement.getAbsenceForParent(studentID, date);

            String status = null;
            if (absence != null) {
                status = StudentDBManagement.insertExcuse(absence, reason);
                Excuse excuse = new Excuse(reason, status);
                absence.setExcuse(excuse);
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
}

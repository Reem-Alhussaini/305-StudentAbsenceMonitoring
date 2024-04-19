package studentabsencemonitoringsystem_swe;

import java.util.Scanner;
import java.io.IOException;

public class StudentAbsenceMonitoringSystem_SWE {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("****** Student Absence Monitoring System ******");
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            handleChoice(scanner, choice);
        } while (choice != 3);
    }
    //-----------------------------------------------------------------------------
    static void displayMenu() {
        System.out.println("Enter a number to select:");
        System.out.println("1. Register Absence or Evaluate Excuse (for Admin)");
        System.out.println("2. Submit Excuse or View Excuse Status (for Parent)");
        System.out.println("3. Quit");
    }
    //-----------------------------------------------------------------------------
    static void handleChoice(Scanner scanner, int choice) {

        if (choice == 1)
            adminFunctions(scanner);
        else if (choice == 2)
            parentFunctions(scanner);
        else if (choice == 3)
            System.out.println("Quitting the program");
        else
            System.out.println("Invalid choice");

    }
    //-----------------------------------------------------------------------------
    static void adminFunctions(Scanner scanner) {
        System.out.println("You're choosing Register Absence or Evaluate Excuse (for Admin)");
        System.out.println("Enter a number to select:");
        System.out.println("1. Register Absence");
        System.out.println("2. Evaluate Excuse");
        int adminChoice = scanner.nextInt();
        //--------------------------------------------------------------------------------------------------------------
        if (adminChoice == 1) {
            //register absence
            try {
                //prompt admin for absence info
                Absence absence = Absence.getAbsenceInfo(scanner);

                //get Student object
                Student student = absence.getStudent();

                //register absence in file
                Admin.registerAbsence(absence, student);

            } catch (IOException e) { System.out.println("Error: couldn't register absence\n" + e);}
        //--------------------------------------------------------------------------------------------------------------
        } else if (adminChoice == 2)  { //evaluate excuse

            //get Absences date
            String date = Absence.getAbsencesDate(scanner);

            //display all excuses with the entered date
            StudentDBManagement.displayExcuses(date);

            //let admin choose which excuse to evaluate
            String id = Admin.getStudentID(scanner);

            Admin.evaluateExcuse(id, date, scanner);
        //--------------------------------------------------------------------------------------------------------------
        } else System.out.println("Invalid choice");
    }
    //-----------------------------------------------------------------------------
    static void parentFunctions(Scanner scanner) {
        System.out.println("You're choosing Add Excuse or View Excuse Status (for Parent)");
        System.out.println("Enter a number to select:");
        System.out.println("1. Submit Excuse");
        System.out.println("2. View Excuse Status");
        int parentChoice = scanner.nextInt();

        //--------------------------------------------------------------------------------------------------------------
        if (parentChoice == 1) { //submit excuse

            Scanner input = new Scanner(System.in);
            //get Student ID
            String id = Parent.getStudentID(input, parentChoice);

            //get absence date
            String date = Parent.getDate(scanner);

            //get excuse reason
            String reason = Parent.getReason(input);

            //submit excuse
            Parent.submitExcuse(id, date, reason);

        //--------------------------------------------------------------------------------------------------------------
        } else if (parentChoice == 2) { //view excuse status
            //get Student ID
            String id = Parent.getStudentID(scanner, parentChoice);

            //get absence date
            String date = Parent.getDate(scanner);

            //view excuse status
            Parent.viewExcuseStatus(id, date);
        //--------------------------------------------------------------------------------------------------------------
        } else System.out.println("Invalid choice");

    }
} //end of main class

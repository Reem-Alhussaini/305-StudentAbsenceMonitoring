package studentabsencemonitoringsystem_swe;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String F_name, String L_name, String id) {
        super(F_name, L_name, id);
    }

    public static void registerAbsence(Absence absence, Student student) {
        System.out.println(FileManagement.insertStudent(student));
        System.out.println(FileManagement.insertAbsence(absence));
    }

    
    public static void evaluateExcuse(int absenceID) {
        // Step 1: find absence object associated with the id given by admin
        Absence absence = Absence.getAbsenceViaAbsenceID(absenceID);

        // Step 2: get excuse object from absence object
        Excuse excuse = absence.getExcuse();

        // Step 3: Display reason for absence and current status of the excuse
        System.out.println("Reason for absence: " + excuse.getReason());
        System.out.println("Current status: " + excuse.getStatus());

        // update the excuse status "only if" the excuse was not evaluated yet
        if(excuse.getStatus() == "waiting for evaluation"){

            // Step 4: Prompt admin to enter the evaluation of the excuse
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter evaluation (accepted/rejected): ");
            String newStatus = scanner.nextLine();
            scanner.close();

            // Step 5: Update excuse status
            excuse.setReason(newStatus);

            // Step 6: Receive message that the status was updated successfully
            System.out.println("Excuse status updated successfully.");
        }
    }
}

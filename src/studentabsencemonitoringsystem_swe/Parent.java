package studentabsencemonitoringsystem_swe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

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
                JOptionPane.showMessageDialog(null, "You may have entered a wrong date or ID, try again");
            }

    }
    //---------------------------------------------------------------------------------------------------
    public static void viewExcuseStatus(String studentID, String date) {
        Excuse excuse = StudentDBManagement.getExcuse(studentID, date);

        if (excuse != null)
            JOptionPane.showMessageDialog(null,"The excuse status is: " + excuse.getStatus());

        else
            JOptionPane.showMessageDialog(null,"absence was not found");
    }
    //---------------------------------------------------------------------------------------------------
    private static void writeExcuseToFile(String studentID, String date, String reason, String status) {
        try {
            File file = new File("studentInfo.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter writer = new FileWriter(file, true); // true for append mode
            writer.write("Student ID: " + studentID + ", Date: " + date + ", Reason: " + reason + ", Status: " + status + "\n");
            writer.close();

            JOptionPane.showMessageDialog(null, "Excuse submitted successfully and saved to file.");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while writing the excuse to file.");
            e.printStackTrace();
        }
    }
}

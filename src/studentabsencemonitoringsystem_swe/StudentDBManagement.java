package studentabsencemonitoringsystem_swe;


import java.sql.*;
import javax.swing.JOptionPane;

public class StudentDBManagement {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/student_absence_monitoring?user=root";
    private static final String USERNAME = "root";
    //change the password to your password
    private static final String PASSWORD = "Ar@121963";

    //------------------------------------------------------------------------------------------------------------------
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }
    //------------------------------------------------------------------------------------------------------------------
    public static void insertDate(Absence absence, Student student) {
        try (Connection con = getConnection()) {
            
            // check if id exists in the database
            int id = Integer.parseInt(student.getId());
            PreparedStatement statement1 = con.prepareStatement("SELECT id FROM students WHERE id = ?");
            statement1.setInt(1, id);
            ResultSet resultSet = statement1.executeQuery();
            
            // if The id you provided doesn't exist in the Database 
            boolean next = resultSet.next();
            if(!next){
                JOptionPane.showMessageDialog(null, "The id you provided doesn't exist in the Database");

            } else { //if The id you provided exist in the Database 
                PreparedStatement statement2 = con.prepareStatement("UPDATE students SET date = ? WHERE id = ?");

                statement2.setString(1, absence.getDate());
                statement2.setString(2, student.getId());

                statement2.executeUpdate();

                // Create an instance of the AbsenceTimer class
                Absence_Timer absenceTimer = new Absence_Timer(absence);
                // Start the timer
                absenceTimer.startTimer();

//            // Optionally, check if the timer is running
//            if (absenceTimer.isTimerRunning()) {
//                System.out.println("Timer is running.");
//            }

//            // When you want to stop the timer
//            absenceTimer.stopTimer();
                JOptionPane.showMessageDialog(null, "Absence date added successfully");
//                System.out.println("Absence date added successfully");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't add absence date, you might have entered a name that doesn't exist in the Database");
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static String insertExcuse(Absence absence, String reason) {
        try (Connection con = getConnection()) {

            PreparedStatement statement = con.prepareStatement(
                    "UPDATE students SET cause_of_absence = ?, evaluation = 'waiting for evaluation' " +
                            "WHERE id = ? AND date = ?");

            statement.setString(1, reason);
            statement.setString(2, absence.getStudent().getId());
            statement.setString(3, absence.getDate());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Excuse added successfully");

            return "waiting for evaluation";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't add excuse");
            e.printStackTrace();
            return null;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static void insertStatus(int id, String date, String status) {
        try (Connection con = getConnection()) {

            PreparedStatement statement = con.prepareStatement(
                    "UPDATE students SET evaluation = ? WHERE id = ? AND date = ?");

            statement.setString(1, status);
            statement.setInt(2, id);
            statement.setString(3, date);

            statement.executeUpdate();
            System.out.println("Status updated successfully");
        } catch (SQLException e) {
            System.out.println("Couldn't update status:");
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static void displayExcuses(String date) {
        try (Connection con = getConnection()) {

            Statement st = con.createStatement();
            ResultSet result = st.executeQuery(
                    "SELECT date, id, cause_of_absence, evaluation FROM students WHERE date = '" + date + "'");

            StringBuilder message = new StringBuilder();
            
            while (result.next()) {
                String dateOfAbsence = result.getString("date");
                int studentId = result.getInt("id");
                String causeOfAbsence = result.getString("cause_of_absence");
                String evaluation = result.getString("evaluation");

                if(causeOfAbsence != null && evaluation != null) {
                    message.append("Date of absence: ").append(dateOfAbsence)
                            .append("\nStudent ID: ").append(studentId)
                            .append("\nReason for absence: ").append(causeOfAbsence)
                            .append("\nCurrent status: ").append(evaluation)
                            .append("\n\n");
                }
            }
            // display message
            JOptionPane.showMessageDialog(null, message.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't display excuses:");
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static Excuse getExcuse(String studentID, String date) {
        try (Connection con = getConnection()) {

            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("SELECT cause_of_absence, evaluation FROM students WHERE date = '" + date +
                            "' AND id = " + Integer.parseInt(studentID));

            if (result.next()) {
                String causeOfAbsence = result.getString("cause_of_absence");
                String evaluation = result.getString("evaluation");
                return new Excuse(causeOfAbsence,evaluation);
            } else {
                JOptionPane.showMessageDialog(null, "No records found for the given id and date.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't display the excuse.");
            e.printStackTrace();
        }
        return null;
    }
    //------------------------------------------------------------------------------------------------------------------
    public static Absence getAbsenceForParent(String studentID, String date) {
        try (Connection con = getConnection()) {

            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("SELECT first_name, last_name FROM students WHERE date = '" + date +
                            "' AND id = " + Integer.parseInt(studentID));

            if (result.next()) {
                String f_name = result.getString("first_name");
                String l_name = result.getString("last_name");
                Student student = new Student(f_name, l_name, studentID);
                return new Absence(student, date, null);
            } else {
                JOptionPane.showMessageDialog(null, "There is no absence registered for this student ID or this date, try again");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while fetching absence information");
            e.printStackTrace();
        }
        return null;
    }
}
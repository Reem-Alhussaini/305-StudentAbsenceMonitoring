package studentabsencemonitoringsystem_swe;


import java.sql.*;

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
//    public static void insertStudent(Student student) {
//        try (Connection con = getConnection()) {
//
//            PreparedStatement statement = con.prepareStatement("INSERT INTO students (id, first_name, last_name) VALUES (?, ?, ?)");
//
//            statement.setString(1, student.getId());
//            statement.setString(2, student.getF_name());
//            statement.setString(3, student.getL_name());
//
//            statement.executeUpdate();
//            System.out.println("Student info inserted");
//        } catch (SQLException e) {
//            System.out.println("Couldn't insert student info:");
//            e.printStackTrace();
//        }
//    }
    //------------------------------------------------------------------------------------------------------------------
    public static void insertDate(Absence absence, Student student) {
        try (Connection con = getConnection()) {

            //check if id exists in the database
            int id = Integer.parseInt(student.getId());
            PreparedStatement statement1 = con.prepareStatement("SELECT id FROM students WHERE id = ?");
            statement1.setInt(1, id);
            ResultSet resultSet = statement1.executeQuery();
            if(!resultSet.next()){
                System.out.println("\nThe id you provided doesn't exist in the Database\n");
                StudentAbsenceMonitoringSystem.adminFunctions();
            }


            PreparedStatement statement2 = con.prepareStatement("UPDATE students SET date = ? WHERE id = ?");

            statement2.setString(1, absence.getDate());
            statement2.setString(2, student.getId());

            statement2.executeUpdate();
            System.out.println("Absence date added successfully");
        } catch (SQLException e) {
            System.out.println("Couldn't add absence date, you might have entered a name that doesn't exist in the Database");
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
            System.out.println("Excuse added successfully");
            return "waiting for evaluation";
        } catch (SQLException e) {
            System.out.println("Couldn't add excuse:");
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

            while (result.next()) {
                String dateOfAbsence = result.getString("date");
                int studentId = result.getInt("id");
                String causeOfAbsence = result.getString("cause_of_absence");
                String evaluation = result.getString("evaluation");

                if(causeOfAbsence != null && evaluation != null) {
                    System.out.println("Date of absence: " + dateOfAbsence);
                    System.out.println("Student ID: " + studentId);
                    System.out.println("Reason for absence: " + causeOfAbsence);
                    System.out.println("Current status: " + evaluation + "\n");
                }
            }
        } catch (SQLException e) {
            System.out.println("Couldn't display excuses:");
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
                System.out.println("No records found for the given id and date.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't display the excuse:");
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
                System.out.println("There is no absence registered for this student ID or this date, try again");
                StudentAbsenceMonitoringSystem.parentFunctions();
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching absence information:");
            e.printStackTrace();
        }
        return null;
    }
}
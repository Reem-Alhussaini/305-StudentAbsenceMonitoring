package studentabsencemonitoringsystem_swe;


import java.sql.*;
public class StudentDBManagement {

    //insert functions---------------------------------------------------------------------------------------------------------
    public static void insertStudent(Student student) { //completed 305

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            String studentInfo = "'"+student.getId() + "','" + student.getF_name() + "','" + student.getL_name()+"'";

            String records = "INSERT INTO students (id,first_name,last_name) VALUES" +
                    "(" + studentInfo + ")";
            st.executeUpdate(records);

            System.out.println("student info inserted");
        } catch (SQLException e) { System.out.println("couldn't insert student info \n" + e);}
    }

    //--------------------------------------------------------------------------------------------------------------------------
    public static void insertDate(Absence absence, Student student){ //completed 305

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            int id = Integer.parseInt(student.getId());
            String date = absence.getDate();

            String records = "UPDATE students " +
                    "SET date = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(records);
            statement.setString(1, date); // Set the date parameter
            statement.setInt(2, id);      // Set the id parameter
            statement.executeUpdate();


            System.out.println("absence date added successfully");
        } catch (SQLException e) { System.out.println("couldn't add absence date, try again \n" ); e.printStackTrace();}
    }
    //--------------------------------------------------------------------------------------------------------------------------

    public static String insertExcuse (Absence absence, String reason) { //completed 305

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            int id = Integer.parseInt(absence.getStudent().getId());
            String date = absence.getDate();

            String check = "SELECT evaluation " +
                    "FROM students " +
                    "WHERE id = " + id + " AND date = '" + date +"'";

            ResultSet resultSet = st.executeQuery(check);

            // Check if resultSet is null or if evaluation is not "unexcused"
            if (resultSet == null || !resultSet.next() || !"unexcused".equals(resultSet.getString("evaluation"))) {
                String records = "UPDATE students " +
                        "SET cause_of_absence = ?, " +
                        "evaluation = 'waiting for evaluation' " +
                        "WHERE id = ? AND date = ?";
                PreparedStatement statement = con.prepareStatement(records);
                statement.setString(1, reason);
                statement.setInt(2, id);
                statement.setString(3, date);
                statement.executeUpdate();
                System.out.println("Excuse added successfully");
                return "waiting for evaluation";

            }
            else {
                System.out.println("The excuse cannot be submitted. The absence has been recorded as an unexcused absence\n" +
                        "The period specified for submitting the excuse have been exceeded");
                return "unexcused";
            }
        } catch (SQLException e) { System.out.println("couldn't add excuse, try again \n" ); e.printStackTrace();}
        return null;
    }

    //--------------------------------------------------------------------------------------------------------------------------
    public static void insertStatus(int id, String date, String status){ //completed 305

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            String records = "UPDATE students " +
                    "SET evaluation = ?"+
                    "WHERE id = ? AND date = ?";

            PreparedStatement statement = con.prepareStatement(records);
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.setString(3, date);
            statement.executeUpdate();

            System.out.println("Status updated successfully");

        } catch (SQLException e) { System.out.println("couldn't update status, try again \n" ); e.printStackTrace();}

    }
    //--------------------------------------------------------------------------------------------------------------------------
    public static void insertUnexcused (Absence absence, String status){ //completed 305

        if(absence != null) {
            try {
                String ConnectionURL = "jdbc:mysql://localhost:3306/students";
                Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
                Statement st = con.createStatement();

                int id = Integer.parseInt(absence.getStudent().getId());
                String date = absence.getDate();

                String records = "UPDATE students " +
                        "SET evaluation = ?"+
                        "WHERE id = ? AND date = ?";

                PreparedStatement statement = con.prepareStatement(records);
                statement.setString(1, status);
                statement.setInt(2, id);
                statement.setString(3, date);
                statement.executeUpdate();

                absence.getExcuse().setStatus(status);
                System.out.println("Status updated successfully");

            } catch (SQLException e) { System.out.println("couldn't update status, try again  \n" ); e.printStackTrace();}
        }
    }

    //end of insert functions--------------------------------------------------------------------------------------------------

    public static void displayExcuses (String date){

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            String records = "SELECT date, id, cause_of_absence, evaluation " +
                    "FROM students " +
                    "WHERE date = '" + date +"'";
            ResultSet result = st.executeQuery(records);

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
            result.close();
        } catch (SQLException e) { System.out.println("couldn't display excuses, try again  \n" ); e.printStackTrace();}
    }

    //----------------------------------------------------------------------------------
    public static Excuse getExcuse(String studentID, String date){

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            int id = Integer.parseInt(studentID);

            String records = "SELECT cause_of_absence, evaluation " +
                    "FROM students " +
                    "WHERE date = '" + date +"' AND id = " + id;
            ResultSet result = st.executeQuery(records);

            if (!result.next()) {
                // No rows found
                System.out.println("No records found for the given id and date.");
            }else {
                String causeOfAbsence = result.getString("cause_of_absence");
                String evaluation = result.getString("evaluation");
                return new Excuse(causeOfAbsence,evaluation);
            }
            result.close();

        } catch (SQLException e) { System.out.println("couldn't display the excuse, try again  \n" ); e.printStackTrace();}
        return null;
    }

    //-----------------------------------------------------------------------------------
    public static Absence getAbsenceForParent (String studentID, String date){

        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";
            Connection con = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");
            Statement st = con.createStatement();

            int id = Integer.parseInt(studentID);

            String records = "SELECT first_name, last_name " +
                    "FROM students " +
                    "WHERE date = '" + date +"' AND id = " + id;
            ResultSet result = st.executeQuery(records);

            if (result.next()) { // Move cursor to the first row
                String f_name = result.getString("first_name");
                String l_name = result.getString("last_name");
                Student student = new Student(f_name, l_name, studentID);
                result.close();
                return new Absence(student, date, null);
            } else {
                System.out.println("There is no absence registered in the file "
                        + "for the student ID: " + studentID + " on the date: " + date);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching absence information: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
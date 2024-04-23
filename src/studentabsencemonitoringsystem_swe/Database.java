package studentabsencemonitoringsystem_swe;
import java.sql.*;

//this class must be run first and never again (one time use)
public class Database {
    //add the jar file
    private static final String CON_URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_absence_monitoring?user=root";
    private static final String USERNAME = "root";
    //change the password to your password
    private static final String PASSWORD = "Ar@121963";
    private static final String DATABASE_NAME = "student_absence_monitoring";

    public static void main(String[] args) {
        //remove comments of the calls to create the database and table
        //createDB();
        //createTable();
        insertStudents();
    }
    //----------------------------------------

    public static void createDB() {
        try (Connection con = DriverManager.getConnection(CON_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {

            st.executeUpdate("CREATE DATABASE " + DATABASE_NAME);
            System.out.println("Database created");

        } catch (SQLException e) {
            System.out.println("Couldn't create student_absence_monitoring database or database already created");
            System.out.println("Check if you changed the password and added the jar file");
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {

            String table = "CREATE TABLE students (" +
                    "id INT PRIMARY KEY, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "date DATE," +
                    "cause_of_absence VARCHAR(200)," +
                    "evaluation VARCHAR(50))";
            st.executeUpdate(table);
            System.out.println("students Table created");


        } catch (SQLException e) {
            System.out.println("Couldn't create table or table already created");
            e.printStackTrace();
        }
    }
    public static void insertStudents(){
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)){
            String[][] records = {
                    {"1", "reem", "alhussaini"},
                    {"2", "walaa", "alsulami"},
                    {"3", "reema", "hesamuddin"},
                    {"4", "alaa", "asghar"}
            };

            String insertQuery = "INSERT INTO students (id, first_name, last_name) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (String[] record : records) {
                preparedStatement.setString(1, record[0]);
                preparedStatement.setString(2, record[1]);
                preparedStatement.setString(3, record[2]);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();

            System.out.println("records inserted successfully!");

        } catch (SQLException e) {
        System.out.println("Couldn't insert records or records already inserted");
        e.printStackTrace();
        }
    }
}
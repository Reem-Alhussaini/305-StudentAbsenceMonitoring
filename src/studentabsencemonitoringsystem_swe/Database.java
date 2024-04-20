package studentabsencemonitoringsystem_swe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//this class must be run first and never again (one time use)
public class Database {
    //add the jar file
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    //change the password to your password
    private static final String PASSWORD = "Ar@121963";
    private static final String DATABASE_NAME = "Students";

    public static void main(String[] args) {
        //remove comments of the calls to create the database and table
        //createDB();
        //createTable();
    }
    //----------------------------------------
    public static void createDB() {
        try (Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {

            st.executeUpdate("CREATE DATABASE " + DATABASE_NAME);
            System.out.println("Database created");

        } catch (SQLException e) {
            System.out.println("SQL statement is not executed!");
            System.out.println("Check if you changed the password and added the jar file");
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try (Connection con = DriverManager.getConnection(DB_URL + "/" + DATABASE_NAME, USERNAME, PASSWORD);
             Statement st = con.createStatement()) {

            String table = "CREATE TABLE students (" +
                    "id INT PRIMARY KEY, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "date DATE," +
                    "cause_of_absence VARCHAR(200)," +
                    "evaluation VARCHAR(50))";
            st.executeUpdate(table);
            System.out.println("Table created");

        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            e.printStackTrace();
        }
    }


}

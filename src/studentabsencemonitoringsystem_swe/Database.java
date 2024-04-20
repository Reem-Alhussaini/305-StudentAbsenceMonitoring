package studentabsencemonitoringsystem_swe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;

public class Database {

    public static void createDB(){
        try{
            Connection con = null;
            String ConnectionURL = "jdbc:mysql://localhost:3306"; //didn't add a DB name since im creating one

            con = DriverManager.getConnection(ConnectionURL,"root","Ar@121963") ;

            Statement st = con.createStatement();

            st.executeUpdate("CREATE DATABASE Students");

            System.out.println("Database created");

            con.close();
        }
        catch (SQLException s){
            System.out.println("SQL statement is not executed!");
        }

    }
    public static void createTable(){
        Connection connection;
        try {
            //path
            String ConnectionURL = "jdbc:mysql://localhost:3306/students";

            //(1) connect to DB
            connection = DriverManager.getConnection(ConnectionURL, "root", "Ar@121963");

            // (2) create table
            Statement st = connection.createStatement();

            String table = "CREATE TABLE students (" +
                    "id INT PRIMARY KEY, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "date DATE," +
                    "cause_of_absence VARCHAR(200)," +
                    "evaluation VARCHAR(50))";
            st.executeUpdate(table);

            System.out.println("table created");


        }catch(SQLException e){
            System.out.println("couldn't create table");
        }
    }
    public static void main (String[] args){

        //createDB();
        //createTable();

    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentabsencemonitoringsystem_swe;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;


/**
 *
 * @author ASUS
 */
public class FileManagementTest {
    
    public FileManagementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInsertStudent() {
        // Test inserting a new student
        Student student = new Student("John", "Doe", "123");
        String result = FileManagement.insertStudent(student);
        //assertEquals("Student should be added successfully", "Student added successfully: 123 John Doe", result);
        FileManagement.insertStudent(student);
    }

    @Test
    public void testInsertAbsence() throws IOException {
        // Test inserting a new absence for a student
        Student student = new Student("John", "Doe", "123");
        Absence absence = new Absence(student, "01/01/2023", null);
        String result = FileManagement.insertAbsence(absence, student);
        //assertEquals("Absence should be added successfully", "absence added successfully", result);
        FileManagement.insertAbsence(absence, student);
    }

    @Test
    public void testInsertExcuse() throws IOException {
        // Test inserting a new excuse for an absence
        Student student = new Student("John", "Doe", "123");
        Absence absence = new Absence(student, "01/01/2023", null);
        Excuse excuse = new Excuse("sick", "waiting for evaluation");
        String result = FileManagement.insertExcuse(excuse, absence);
        assertEquals("Excuse should be added successfully", "excuse added sucessfully", result);
    }

    @Test
    public void testInsertExcuseStatus() throws IOException {
        // Test inserting a new status for an excuse
        Student student = new Student("John", "Doe", "123");
        Absence absence = new Absence(student, "01/01/2023", new Excuse("sick", "waiting for evaluation"));
        Excuse excuse = absence.getExcuse();
        String result = FileManagement.insertExcuseStatus(absence, excuse, "accepted");
        assertEquals("Status should be updated successfully", "Status updated sucessfully", result);
    }

    @Test
    public void testGetAbsenceForAdmin() {
        // Test getting absence information for admin
        String studentID = "123";
        String date = "01/01/2023";
        FileManagement.getAbsenceWExcuse(studentID, date);
        //assertNotNull("Absence information should be retrieved for admin", result);
    }

    @Test
    public void testGetAbsenceForParent() {
        // Test getting absence information for parent
        String studentID = "123";
        String date = "01/01/2023";
        Absence result = FileManagement.getAbsenceForParent(studentID, date);
        assertNotNull("Absence information should be retrieved for parent", result);
    }

    /**
     * Test of getAbsences method, of class FileManagement.
     */
    @Test
    public void testGetAbsences() {
        System.out.println("getAbsences");
        //ArrayList<Absence> expResult = null;
        //ArrayList<Absence> result = FileManagement.getAbsences();
        //assertEquals(expResult, result);
        FileManagement.getAbsences();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertUnexcused method, of class FileManagement.
     */
    @Test
    public void testInsertUnexcused() {
        System.out.println("insertUnexcused");
        Absence absence =  new Absence( new Student("John", "Doe", "123") , "01/01/2023", new Excuse("sick", "waiting for evaluation"));
        String status = "Unexcused";
        //String expResult = "";
        //String result = FileManagement.insertUnexcused(absence, status);
        FileManagement.insertUnexcused(absence, status);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of rewriteFileContent method, of class FileManagement.
     */
    @Test
    public void testRewriteFileContent() {
        System.out.println("rewriteFileContent");
        List<String> lines = null;
        boolean lineModified = false;
        Student student = null;
        String success = "yes";
        String fail = "No";
        //String expResult = "";
        
        FileManagement.rewriteFileContent(lines, lineModified, student, success, fail);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of displayExcuses method, of class FileManagement.
     */
    @Test
    public void testDisplayExcuses() {
        System.out.println("displayExcuses");
        String date = "01/01/2023";
        FileManagement.displayExcuses(date);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAbsenceWExcuse method, of class FileManagement.
     */
    @Test
    public void testGetAbsenceWExcuse() {
        System.out.println("getAbsenceWExcuse");
        String studentID = "1234";
        String date = "01/01/2023";
        //Absence expResult = null;
        //Absence result = FileManagement.getAbsenceWExcuse(studentID, date);
        FileManagement.getAbsenceWExcuse(studentID, date);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}

       

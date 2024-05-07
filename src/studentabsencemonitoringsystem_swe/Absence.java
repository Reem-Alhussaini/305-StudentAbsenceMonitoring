package studentabsencemonitoringsystem_swe;

import java.util.Scanner;

//made a static counter to increment the id 
public class Absence {

    private String Date;
    private Student student;
    private Excuse excuse;
    long startTime;//Changed access level to package-private
    private AbsenceTimer timer;
 
//removed id from constructor 
    public Absence(Student student, String Date, Excuse excuse) {
        this.Date = Date;
        this.student = student;
        this.excuse = excuse;
        this.startTime = System.currentTimeMillis();
        this.timer = new AbsenceTimer(this);
    }
    
    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setExcuse(Excuse excuse) {
        this.excuse = excuse;
    }

    public String getDate() {
        return Date;
    }

    public Student getStudent() {
        return student;
    }

    public Excuse getExcuse() {
        return excuse;
    }

    public long getStartTime() {
        return startTime;
    }


//-----------------------------------------------------------------------------

    public static Absence getAbsenceInfo(String fname, String lname, String id, String date) {

        Student student = new Student(fname, lname, id);

        return new Absence(student, date, null);
    }
}
package studentabsencemonitoringsystem_swe;

public class Absence {

    private String Date;
    private Student student;
    private Excuse excuse;
    long startTime;//Changed access level to package-private

 
//removed id from constructor 
    public Absence(Student student, String Date, Excuse excuse) {
        this.Date = Date;
        this.student = student;
        this.excuse = excuse;
        this.startTime = System.currentTimeMillis();
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
package studentabsencemonitoringsystem_swe;

public class Absence_Timer implements Runnable {

    private Thread thread;
    private Absence absence;
    //private static final long TIMEOUT = 48 * 60 * 60 * 1000; // 48 hours in milliseconds
    private static final long TIMEOUT = 120000; // two minutes

    public Absence_Timer(Absence absence) {
        this.absence = absence;
    }

    public void startTimer() {
        thread = new Thread(this);
        thread.start();
    }

    public void stopTimer() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    public boolean isTimerRunning() {
        return thread != null && thread.isAlive();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(TIMEOUT);
            handleTimeout();
        } catch (InterruptedException e) {
            // Timer was interrupted, do nothing
        }
    }

    private void handleTimeout() {
        String id1 = absence.getStudent().getId();
        String date = absence.getDate();
        Excuse excuse = StudentDBManagement.getExcuse(id1, date);

        if (excuse == null || excuse.getReason() == null) {
            int id = Integer.parseInt(absence.getStudent().getId());
            StudentDBManagement.insertStatus(id, absence.getDate(), "unexcused");
            System.out.println("timer expired, status changed to unexcused");
        }
        else{
            stopTimer();
            System.out.println("timer stooped");
        }
    }
}
package studentabsencemonitoringsystem_swe;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GUI_Server extends JFrame {
    private JTextArea logTextArea;

    public GUI_Server() {
        super("Server");

        logTextArea = new JTextArea();
        add(logTextArea);

        setSize(600, 505);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void appendLog(String log) {
        logTextArea.append(log + "\n");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentabsencemonitoringsystem_swe;

/**
 *
 * @author a
 */
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

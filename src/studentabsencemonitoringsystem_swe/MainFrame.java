package studentabsencemonitoringsystem_swe;
//the border layout divides the interface into five areas
//north,south,east,west, and center
//we only need the north to create the form, and
//the center part to create the welcome lable, and
//the south part to create two buttons

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {

    final private Font mainFont = new Font("Helvetica Neue", 0, 18);
	final private Font Bfont = new Font("Helvetica Neue", 0, 14);

    public void ParentOrAdmin() {
        //components --------------------------------------------------------------
        JLabel user = new JLabel("Are you an Admin or a Parent?");
        user.setFont(mainFont);
        user.setForeground(Color.BLACK);
        user.setHorizontalAlignment(SwingConstants.CENTER);

        JButton BParent = new JButton("Parent");
        BParent.setFont(Bfont);
        BParent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame("Parent Log In");
            }
        });

        JButton BAdmin = new JButton("Admin");
        BAdmin.setFont(Bfont);
        BAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLoginFrame("Admin Log In");
            }
        });

        //create the panel that will contain the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(BParent);
        buttonsPanel.add(BAdmin);

        //create the form panel, north --------------------------------------------
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH; // Allow label to stretch both horizontally and vertically
        formPanel.setBackground(Color.WHITE);

        //add components
        formPanel.add(user, gbc);

        //Main Panel---------------------------------------------------------------
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(72, 168, 184));

        //add form panel to main panel - Center
        mainPanel.add(formPanel, BorderLayout.CENTER);

        //add button panels
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        //add firstP to the frame
        add(mainPanel);
        //initializations
        setTitle("Student Absence Monitoring System");
        setSize(400, 300);
        setMaximumSize(new Dimension(200, 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void openLoginFrame(String title) {
        JFrame loginFrame = new JFrame();
        loginFrame.setTitle(title);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Components for login frame
        JLabel logIn = new JLabel("Log in");
        logIn.setFont(Bfont);
        logIn.setForeground(Color.BLACK);
        logIn.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel idL = new JLabel("ID");
        idL.setFont(Bfont);
        idL.setForeground(Color.BLACK);
        idL.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField idF = new JTextField();
        idF.setFont(Bfont);
        idF.setPreferredSize(new Dimension(200, 50)); // Set preferred size

        JLabel passwordL = new JLabel("Password");
        passwordL.setFont(Bfont);
        passwordL.setForeground(Color.BLACK);
        passwordL.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField passwordF = new JTextField();
        passwordF.setFont(Bfont);
        passwordF.setPreferredSize(new Dimension(200, 50)); // Set preferred size

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(Bfont);
        backButton.setForeground(Color.BLACK);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        backButton.setPreferredSize(new Dimension(90, 25)); // Adjust button size

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose(); // Close the login frame
                // Show the parent or admin selection frame
                ParentOrAdmin();
            }
        });

        // Panel for the back button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        

        //ok button
        JButton ok = new JButton("OK");
        ok.setForeground(Color.BLACK);
        ok.setHorizontalAlignment(SwingConstants.CENTER);
        ok.setPreferredSize(new Dimension(90, 25));

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose(); // Close the login frame
                // Show the parent or admin selection frame
                if(title.equals("Parent Log In")){
                GUIParentWindow parentWindow = new GUIParentWindow();
                parentWindow.setVisible(true);
                }
                else{
                    admin();
                }
            }
        });

        JPanel fieldPanel = new JPanel();
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setLayout(new GridLayout(3, 2, 5, 5));
        fieldPanel.add(idL);
        fieldPanel.add(idF);
        fieldPanel.add(passwordL);
        fieldPanel.add(passwordF);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 5, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(backButtonPanel); // Add the back button panel
        formPanel.add(logIn);
        formPanel.add(fieldPanel);
        formPanel.add(ok);

        loginFrame.getContentPane().add(formPanel, BorderLayout.CENTER);

        loginFrame.setSize(400, 300);
        loginFrame.setMaximumSize(new Dimension(200, 100)); // Adjusts the size of the frame to fit its contents
        loginFrame.setVisible(true);

    }


    private void admin() {
        String[] adminOptions = {"Register Absence", "Evaluate Excuse"};

        int choice = JOptionPane.showOptionDialog(null,
                "Do you want to Register Absence or Evaluate Excuse?",
                "Admin",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                adminOptions,
                adminOptions[0]);

        switch (choice) {
            case 0:
                registerAbsence();
                break;
            case 1:
                evaluateExcuse();
                break;
            default:
                // Invalid choice
                JOptionPane.showMessageDialog(null, "Invalid choice");
                break;
        }
    }

    private void registerAbsence() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField idField = new JTextField(10);
        JTextField dateField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Enter Student first name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Enter Student last name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Enter Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Enter absence Date:"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Register Absence", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Handle registration of absence
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String studentID = idField.getText();
            String absenceDate = dateField.getText();
            // Perform registration
        }
    }

    private void evaluateExcuse() {
        JTextField dateField = new JTextField(10);
        JTextField idField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Enter the date of the absences you want to evaluate:"));
        panel.add(dateField);
        panel.add(new JLabel("Enter the id of the student whose excuse you want to evaluate:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Evaluate Excuse", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Handle evaluation of excuse
            String evalDate = dateField.getText();
            String studentIDToEvaluate = idField.getText();
            // Perform evaluation
        }
    }

    public static void main(String[] args) {
        MainFrame parentOrAdmin = new MainFrame();
        parentOrAdmin.ParentOrAdmin();

    }
}

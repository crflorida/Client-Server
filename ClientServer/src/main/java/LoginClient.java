// Project by Christina Grant
//  Application that uses a socket connection to allow a client
//  -to specify a file name of a text file and have the server send
//  -the contents of the file or indicate the file does not exist.
//  The server contains a text based password file username & password.
//  The client must pass a valid username and password to establish a
//  -connection with the Server.
// ***Important run Server class first and then run Client class***
// ***Username=admin, Password=password***

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class LoginClient extends JFrame {

    private static final int FRAME_WIDTH = 250;
    private static final int FRAME_HEIGHT = 200;
    private static final long serialVersionUID = 1L;
    private File userData;
    static JTextField userNameField;
    static JTextField passwordField;

    public LoginClient() throws FileNotFoundException {
        createComponents();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void createComponents() throws FileNotFoundException {
        userNameField = new JTextField(10);
        passwordField = new JTextField(10);

        JLabel userNameLabel = new JLabel("User Name");
        JLabel passwordLabel = new JLabel("Password");

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        JPanel panel = new JPanel();
        panel.add(userNameField);
        panel.add(userNameLabel);
        panel.add(passwordField);
        panel.add(passwordLabel);
        panel.add(loginButton);
        panel.add(exitButton);
        add(panel);

        ActionListener exitListener = new ClickListener1();
        ActionListener loginListener = new ClickListener2();
        exitButton.addActionListener(exitListener);
        loginButton.addActionListener(loginListener);
    }

    public class ClickListener1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            //System.exit(0);
        }
    }

    public class ClickListener2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            File inputFile = new File("accounts.txt");

            String userNameInput = userNameField.getText();
            String passwordInput = passwordField.getText();

            try {
                try ( Scanner in = new Scanner(new File("accounts.txt"))) {
                    while (in.hasNextLine()) {
                        String s = in.nextLine();
                        String[] sArray = s.split(",");

                        System.out.println(sArray[0]); //Just to verify that file is being read
                        System.out.println(sArray[1]);

                        if (userNameInput.equals(sArray[0]) && passwordInput.equals(sArray[1])) {
                            JOptionPane.showMessageDialog(null,
                                    "Login Successful", "Success!",
                                    JOptionPane.INFORMATION_MESSAGE);

                            Client application = new Client();
                            
                             application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Invalid Username / Password Combo", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "User Database Not Found", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}

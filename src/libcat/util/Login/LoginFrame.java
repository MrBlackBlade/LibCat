package libcat.util.Login;

import libcat.Main;
import libcat.util.FileSystemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame {
    LoginFrame loginFrameReference;
    private FileSystemManager fs;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public LoginFrame() {
        fs = new FileSystemManager();

        loginFrameReference = this;

        setTitle("libcat.util.Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add components to the frame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(registerButton, gbc);

        gbc.gridx = 1;
        add(loginButton, gbc);


        // Action listeners
        loginButton.addActionListener(new ActionListener() {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            public void actionPerformed(ActionEvent e) {
                // Perform authentication logic here
                fs.readFile();
                fs.checkForUser(usernameField.getText(), new String(passwordField.getPassword()));
                System.out.println(password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the registration form
                RegisterFrame registerFrame = new RegisterFrame(loginFrameReference);
                registerFrame.setVisible(true);
                setVisible(false);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }
}

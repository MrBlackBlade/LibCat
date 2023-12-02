package libcat.util.Login;

import libcat.util.AuthenticationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    LoginFrame loginFrameReference;
    private JTextField usernameField;
    private JPasswordField passwordField;
    public LoginFrame() {

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



            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Perform authentication logic here
                if(AuthenticationSystem.credentialsMatch(username, password)){
                    // Main window will be added here
                    JOptionPane.showMessageDialog(null, "Welcome!");
                }
                else {
                    //delete system32
                    JOptionPane.showMessageDialog(null, "User not found.");
                }

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

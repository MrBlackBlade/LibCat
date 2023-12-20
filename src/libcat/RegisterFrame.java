package libcat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

class RegisterFrame extends JFrame {
    private final RegisterFrame registerFrameReference;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField phoneNumberField;
    private final JTextField emailField;

    public RegisterFrame(LoginFrame loginFrameReference) {
        registerFrameReference = this;

        // Title and icon
        setTitle("Register Form");
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setPreferredSize(new Dimension(300, 300));
        setLayout(new GridBagLayout());

        Insets insets = new Insets(0,5,2,5);

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        GridBagConstraints gbcUsernameLabel = new GridBagConstraints();
        gbcUsernameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcUsernameLabel.gridy = 0;
        gbcUsernameLabel.gridx = 0;
        gbcUsernameLabel.insets = insets;

        usernameField = new JTextField(20);
        GridBagConstraints gbcUsernameField = new GridBagConstraints();
        gbcUsernameField.fill = GridBagConstraints.HORIZONTAL;
        gbcUsernameField.gridy = 1;
        gbcUsernameField.gridx = 0;
        gbcUsernameField.insets = insets;

        JLabel passwordLabel = new JLabel("Password:");
        GridBagConstraints gbcPasswordLabel = new GridBagConstraints();
        gbcPasswordLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPasswordLabel.gridy = 2;
        gbcPasswordLabel.gridx = 0;
        gbcPasswordLabel.insets = insets;

        passwordField = new JPasswordField(20);
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
        gbcPasswordField.gridy = 3;
        gbcPasswordField.gridx = 0;
        gbcPasswordField.insets = insets;

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        GridBagConstraints gbcPhoneNumberLabel = new GridBagConstraints();
        gbcPhoneNumberLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPhoneNumberLabel.gridy = 4;
        gbcPhoneNumberLabel.gridx = 0;
        gbcPhoneNumberLabel.insets = insets;

        phoneNumberField = new JTextField(20);
        GridBagConstraints gbcPhoneNumberField = new GridBagConstraints();
        gbcPhoneNumberField.fill = GridBagConstraints.HORIZONTAL;
        gbcPhoneNumberField.gridy = 5;
        gbcPhoneNumberField.gridx = 0;
        gbcPhoneNumberField.insets = insets;

        JLabel emailLabel = new JLabel("E-mail:");
        GridBagConstraints gbcEmailLabel = new GridBagConstraints();
        gbcEmailLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcEmailLabel.gridy = 6;
        gbcEmailLabel.gridx = 0;
        gbcEmailLabel.insets = insets;

        emailField = new JTextField(20);
        GridBagConstraints gbcEmailField = new GridBagConstraints();
        gbcEmailField.fill = GridBagConstraints.HORIZONTAL;
        gbcEmailField.gridy = 7;
        gbcEmailField.gridx = 0;
        gbcEmailField.insets = insets;

        JButton registerButton = new JButton("Register");
        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
        gbcRegisterButton.fill = GridBagConstraints.HORIZONTAL;
        //gbcRegisterButton.weightx = 0.7;
        gbcRegisterButton.gridy = 8;
        gbcRegisterButton.gridx = 0;
        gbcRegisterButton.insets = insets;


        //usernameLabel.setBounds(11, 2, 100, 30);
        add(usernameLabel, gbcUsernameLabel);

        //passwordLabel.setBounds(11, 39, 100, 30);
        add(passwordLabel, gbcPasswordLabel);

        //usernameField.setBounds(12, 25, 361, 21);
        add(usernameField, gbcUsernameField);

        //passwordField.setBounds(12, 62, 361, 21);
        add(passwordField, gbcPasswordField);

        add(phoneNumberLabel, gbcPhoneNumberLabel);
        add(phoneNumberField, gbcPhoneNumberField);

        add(emailLabel, gbcEmailLabel);
        add(emailField, gbcEmailField);

        //registerButton.setBounds(92, 112, 200, 20);
        add(registerButton, gbcRegisterButton);

        // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] userDetails = {
                        usernameField.getText(),
                        new String(passwordField.getPassword()),
                        phoneNumberField.getText(),
                        emailField.getText()
                };
                //Perform registration logic here
                HashMap<String, Boolean> userValidations = AuthenticationSystem.Validation.getUsernameValidations(userDetails[0]);
                HashMap<String, Boolean> passwordValidations = AuthenticationSystem.Validation.getPasswordValidations(userDetails[1]);
                HashMap<String, Boolean> emailValidations = AuthenticationSystem.Validation.getEmailValidations(userDetails[2]);

                if (
                        !userValidations.get("AllChecksPass")
                        || !passwordValidations.get("AllChecksPass")
                        || !emailValidations.get("AllChecksPass")
                ) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (String key: userValidations.keySet()) {
                        if (userValidations.get(key) && !key.equals("AllChecksPass")) { errorMessage.append(key).append("\n"); }
                    }
                    for (String key: passwordValidations.keySet()) {
                        if (passwordValidations.get(key) && !key.equals("AllChecksPass")) { errorMessage.append(key).append("\n"); }
                    }
                    for (String key: emailValidations.keySet()) {
                        if (emailValidations.get(key) && !key.equals("AllChecksPass")) { errorMessage.append(key).append("\n"); }
                    }
                    JOptionPane.showMessageDialog(RegisterFrame.this, errorMessage);
                }
                else {
                    AuthenticationSystem.registerNewUser(userDetails);
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration successful!\nUsername: " + userDetails[0]);
                    loginFrameReference.setVisible(true);
                    dispatchEvent(new WindowEvent(registerFrameReference, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginFrameReference.setVisible(true);
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}

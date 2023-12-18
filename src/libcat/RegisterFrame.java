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

    public RegisterFrame(LoginFrame loginFrameReference) {
        registerFrameReference = this;

        // Title and icon
        setTitle("Register Form");
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setPreferredSize(new Dimension(400, 200));
        setLayout(null);

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");

        // Add components to the frame using GridBagConstraints
        usernameLabel.setBounds(11, 2, 100, 30);
        add(usernameLabel);

        passwordLabel.setBounds(11, 39, 100, 30);
        add(passwordLabel);

        usernameField.setBounds(12, 25, 361, 21);
        add(usernameField);

        passwordField.setBounds(12, 62, 361, 21);
        add(passwordField);

        registerButton.setBounds(92, 112, 200, 20);
        add(registerButton);

        // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] userCredentials = {usernameField.getText(), new String(passwordField.getPassword())};
                //Perform registration logic here
                HashMap<String, Boolean> userValidations = AuthenticationSystem.Validation.getUsernameValidations(userCredentials[0]);
                HashMap<String, Boolean> passwordValidations = AuthenticationSystem.Validation.getPasswordValidations(userCredentials[1]);

                if (!userValidations.get("AllChecksPass") || !passwordValidations.get("AllChecksPass")) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (String key: userValidations.keySet()) {
                        if (userValidations.get(key) && !key.equals("AllChecksPass")) { errorMessage.append(key).append("\n"); }
                    }
                    for (String key: passwordValidations.keySet()) {
                        if (passwordValidations.get(key) && !key.equals("AllChecksPass")) { errorMessage.append(key).append("\n"); }
                    }
                    JOptionPane.showMessageDialog(RegisterFrame.this, errorMessage);
                }
                else {
                    AuthenticationSystem.registerNewUser(userCredentials);
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration successful!\nUsername: " + userCredentials[0]);
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

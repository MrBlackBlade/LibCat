package libcat.util.Login;

import libcat.util.AuthenticationSystem;
import libcat.util.FileSystemManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private RegisterFrame registerFrameReference;
    public RegisterFrame(LoginFrame loginFrameReference) {

        registerFrameReference = this;

        setTitle("Register Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
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

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] userCredentials = {usernameField.getText(), new String(passwordField.getPassword())};
                //Perform registration logic here
                if(AuthenticationSystem.registerNewUser(userCredentials)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration successful!\nUsername: " + userCredentials[0]);
                    loginFrameReference.setVisible(true);
                    dispatchEvent(new WindowEvent(registerFrameReference, WindowEvent.WINDOW_CLOSING));
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this, String.format("User %s already exists!", userCredentials[0]));
                }
            }
        });
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                loginFrameReference.setVisible(true);
            }
        });
        pack();
        setLocationRelativeTo(null);
    }
}

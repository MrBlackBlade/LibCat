package libcat;

import libcat.util.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private final LoginFrame loginFrameReference;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        loginFrameReference = this;

        // Title and icon
        setTitle("LibCat");
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(400, 200));
        setLayout(null);

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add components to the frame
        usernameLabel.setBounds(11, 2, 100, 30);
        add(usernameLabel);

        passwordLabel.setBounds(11, 39, 100, 30);
        add(passwordLabel);

        usernameField.setBounds(12, 25, 361, 21);
        add(usernameField);

        passwordField.setBounds(12, 62, 361, 21);
        add(passwordField);

        registerButton.setBounds(92, 98, 200, 20);
        add(registerButton);

        loginButton.setBounds(92, 126, 200, 20);
        add(loginButton);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Perform authentication logic here
                if (AuthenticationSystem.credentialsMatch(username, password)) {
                    User user = (User) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.NAME_EQUAL, username).get(0);
                    if (user.getType().equalsIgnoreCase("admin")){
                        new AdminFrame();
                        setVisible(false);
                    }
                    else {
                        new MainFrame(user);
                        setVisible(false);
                    }
                } else {
                    //delete system32
                    JOptionPane.showMessageDialog(null, "Username or password invalid.");
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

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}

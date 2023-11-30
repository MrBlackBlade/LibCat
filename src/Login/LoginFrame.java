package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame {

    File f = new File("E:\\LibCat");
    int line;

    public void readFile() {
        try {
            FileReader fr = new FileReader(f + "\\logins.txt");
            System.out.println("file exists!");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(f + "\\logins.txt");
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    public void addData(String user, String password) {
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            for (int i = 0; i < line; i++) {
                raf.readLine();
            }
            raf.writeBytes(user + "\n");
            raf.writeBytes(password + "\n");
            raf.writeBytes("\n");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void countLines() {
        try {
            line = 0;
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            for (int i = 0; raf.readLine() != null; i++) {
                line++;
            }
            System.out.println("number of lines:" + line);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*void checkData(String user, String pass) {

        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            String line = raf.readLine();
            username = line.substring(9);
            password = raf.readLine().substring(9);
            if (user.equals(username) & pass.equals(password)) {
                JOptionPane.showMessageDialog(null, "Password matched");
            } else {
                JOptionPane.showMessageDialog(null, "Wrong user/Password");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
    void logic(String usr, String pswd) {
        boolean user_found = false;
        System.out.println(usr);
        System.out.println(pswd);
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logins.txt", "rw");
            for (int i = 0; i < line; i += 2) {
                System.out.println("count " + i);

                String forUser = raf.readLine();
                String forPswd = raf.readLine();

                if (usr.equals(forUser) && pswd.equals(forPswd)) {
                    user_found = true;
                    raf.readLine();
                    break;
                } else {
                    user_found = false;
                    raf.readLine();
                }
            }
                if(user_found){
                    // Main window will be added here
                    JOptionPane.showMessageDialog(null, "Welcome !");
                }
                else {
                    //delete system32
                    JOptionPane.showMessageDialog(null, "Please Register First.");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JTextField usernameField;
    private JPasswordField passwordField;
    public LoginFrame() {
        setTitle("Login Form");
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
                readFile();
                countLines();
                //checkData(username, password);
                logic(usernameField.getText(), new String(passwordField.getPassword()));
                System.out.println(password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the registration form
                RegisterFrame registerFrame = new RegisterFrame();
                registerFrame.setVisible(true);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }
}

package libcat;

import libcat.util.*;
import libcat.util.Login.LoginFrame;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library.initialize();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });

    }
}
package libcat;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Library.initialize();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
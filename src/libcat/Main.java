package libcat;

import libcat.util.*;
import libcat.util.GUI.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Library.initialize();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
//        SwingUtilities.invokeLater(() -> {
//            LayoutTest layoutTest = new LayoutTest("user");
//            layoutTest.setVisible(true);
//        });
    }
}
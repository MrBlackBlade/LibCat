package libcat;

import libcat.util.Borrower;
import libcat.util.Customer;
import libcat.util.Transaction;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		Library.initialize();
		SwingUtilities.invokeLater(() -> {
			LoginFrame loginFrame = new LoginFrame();
			loginFrame.setVisible(true);
		});
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Exiting application... Cleaning up");
			FileSystemManager.updateData();
			System.out.println("Exiting application... Clean-up complete!");
		}));
	}
}
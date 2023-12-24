package libcat;

import libcat.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RateFrame
		extends JFrame
		implements FrameEnvironment {

	private enum RadioSelect {
		LIKE,
		DISLIKE,
		RADIO_SELECT_MAX,
	}

	static RadioSelect choice = null;
	private Customer customer;
	private boolean rating = false;
	private final JTextField commentField;

	private MainFrame mainFrameReference;

	protected ArrayList<Rating> pendingRatings;

	public RateFrame(User user, Book book, MainFrame mainFrameReference) {

		this.mainFrameReference = mainFrameReference;
		if (user.getType().equalsIgnoreCase("customer")) {
			this.customer = (Customer) user;
		} else if (user.getType().equalsIgnoreCase("borrower")) {
			this.customer = (Borrower) user;
		}

		// Set the background color of the frame
		Color frameBackgroundColor = new Color(242, 231, 199);
		getContentPane().setBackground(frameBackgroundColor);

		// Title and icon
		setTitle("LibCat");
		ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
		setIconImage(icon.getImage());

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrameReference.searchButton.doClick();
			}
		});

		// Use layout manager
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setPreferredSize(new Dimension(600, 400));

		// Create components
		JLabel commentLabel = new JLabel("Comment:");
		commentField = new JTextField();
		JButton submitButton = new JButton("Submit");
		JRadioButton radioButton1 = new JRadioButton("Like");
		JRadioButton radioButton2 = new JRadioButton("Dislike");

		// Set the background color of the radio buttons
		Color radioBackgroundColor = frameBackgroundColor;
		radioButton1.setBackground(radioBackgroundColor);
		radioButton2.setBackground(radioBackgroundColor);

		// Add radio buttons to a panel with horizontal BoxLayout
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(radioButton1);
		radioButtonGroup.add(radioButton2);
		radioPanel.add(radioButton1);
		radioPanel.add(radioButton2);

		// Set the background color of the comment area
		Color commentBackgroundColor = Color.WHITE;
		commentField.setBackground(commentBackgroundColor);

		// Set the number of rows and columns for the comment area
		commentField.setColumns(30);

		// Set GridBagConstraints for commentLabel
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		add(commentLabel, gbc);

		// Set GridBagConstraints for commentField
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(commentField, gbc);

		// Set GridBagConstraints for radioPanel
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		add(radioPanel, gbc);

		// Set GridBagConstraints for submitButton
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(submitButton, gbc);

		// ActionListener for radio buttons
		ActionListener radioListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (radioButton1.isSelected()) {
					choice = RadioSelect.LIKE;
					rating = true;

				} else if (radioButton2.isSelected()) {
					choice = RadioSelect.DISLIKE;
					rating = false;
				}
			}
		};

		// Add ActionListener to radio buttons
		radioButton1.addActionListener(radioListener);
		radioButton2.addActionListener(radioListener);

		// ActionListener for submit button
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice != null) {
					String comment = commentField.getText();
					customer.rateBook(book, rating, comment);
					JOptionPane.showMessageDialog(null, "Your Reaction was submitted successfully.");

					Library.updateRatings();

				} else {
					JOptionPane.showMessageDialog(null, "Please State whether you Like or Dislike the Book.");
				}
			}
		});

		pack();  // Pack the components
		setLocationRelativeTo(null);  // Center the window
		setVisible(true);
	}
}






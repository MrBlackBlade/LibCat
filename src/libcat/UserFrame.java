package libcat;

import libcat.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame
		extends JFrame
		implements FrameEnvironment {

	static int panelHeight = 300;
	static JPanel containerPanel = new JPanel();
	static Border border = BorderFactory.createLineBorder(Color.black, 3);

	private Customer customer;

	public UserFrame(User user) {
		if (user.getType().equalsIgnoreCase("customer")) {

			this.customer = (Customer) user;

		} else if (user.getType().equalsIgnoreCase("borrower")) {
			this.customer = (Borrower) user;

		}
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		// Window Size, Icon and Name
		ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1280, 900);
		setResizable(false);
		setTitle("LibCat");
		setIconImage(icon.getImage());
		getContentPane().setBackground(C_ListBG);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(1280, 175));

		// Panel on top and to the left of welcomePanel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		topPanel.setPreferredSize(new Dimension(200, 150)); // Set your preferred size
		topPanel.setBackground(C_WelcomeBG); // Set your desired background color
		topPanel.setBounds(0, 140, 1280, 40);

		JButton transButton = new JButton("Transactions");
		transButton.setBackground(C_ButtonBG);
		transButton.setForeground(Color.WHITE);
		transButton.setPreferredSize(new Dimension(120, 26));

		JButton ordersButton = new JButton("Orders");
		ordersButton.setBackground(C_ButtonBG);
		ordersButton.setForeground(Color.WHITE);
		ordersButton.setPreferredSize(new Dimension(120, 26));

		topPanel.add(transButton);
		topPanel.add(ordersButton);

		layeredPane.add(topPanel, JLayeredPane.PALETTE_LAYER);

		// Welcome Panel
		JPanel welcomePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		welcomePanel.setBackground(C_WelcomeBG);
		welcomePanel.setBounds(0, 0, 1280, 150);

		JLabel welcomeLabel = new JLabel("Welcome " + user.getName());
		welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setVerticalAlignment(JLabel.TOP);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));

		// Components placing
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0); // Add some space between components
		welcomePanel.add(welcomeLabel, gbc);

		layeredPane.add(welcomePanel, JLayeredPane.DEFAULT_LAYER);

		// Book Panels
		JScrollPane scrollPane = new JScrollPane(containerPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		ordersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orderHistory();

				SwingUtilities.invokeLater(() -> {
					setLocationRelativeTo(null);
					setVisible(true);
					scrollPane.getVerticalScrollBar().setValue(0);
				});
			}
		});
		transButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTransactionHistory();

				SwingUtilities.invokeLater(() -> {
					setLocationRelativeTo(null);
					setVisible(true);
					scrollPane.getVerticalScrollBar().setValue(0);
				});
			}
		});

		// Add components
		add(layeredPane, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		ordersButton.doClick();
		transButton.doClick();

		SwingUtilities.invokeLater(() -> {
			setLocationRelativeTo(null);
			setVisible(true);
			scrollPane.getVerticalScrollBar().setValue(0);
		});

	}

	private void orderHistory() {
		boolean orderFound = false;
		containerPanel.removeAll();

		for (Order order : customer.getOrderHistory()) {

			//Main Panel
			JPanel ListItemPanel = new JPanel();
			ListItemPanel.setLayout(new GridBagLayout());
			ListItemPanel.setBackground(C_ListBG);
			ListItemPanel.setPreferredSize(new Dimension(1280, panelHeight));
			ListItemPanel.setBorder(border);

			//Book Image
			ImageIcon userImage = new ImageIcon(FileSystemManager.cwd + "order.png");
			Image scaledImage = userImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size
			ImageIcon scaledBookImage = new ImageIcon(scaledImage);
			JLabel imageLabel = new JLabel(scaledBookImage);

			//Book Properties
			imageLabel.setHorizontalTextPosition(JLabel.RIGHT);
			imageLabel.setVerticalTextPosition(JLabel.CENTER);

			//imageLabel.setPreferredSize(new Dimension());
			imageLabel.setIconTextGap(15);

			//GBCs
			GridBagConstraints gbcImageLabel = new GridBagConstraints();
			gbcImageLabel.fill = GridBagConstraints.HORIZONTAL;
			//gbcImageLabel.weightx = 1.0;
			gbcImageLabel.gridx = 0;
			gbcImageLabel.gridy = 0;
			gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcImageLabel.insets = new Insets(0, 48, 0, 0); // Add space between components

			//Book Text
			JTextArea bookLabel = new JTextArea(2, 20);
			bookLabel.setText(String.format(
					"ID: %s\n\nTitle: %s\n\nQuantity: %s\n\nPrice: $%s",
					order.getID(),
					order.getBook().getTitle(),
					order.getQuantity(),
					order.getTotalPrice()
			));
			bookLabel.setFont(new Font("Arial", Font.BOLD, 22));
			bookLabel.setWrapStyleWord(true);
			bookLabel.setLineWrap(true);
			bookLabel.setOpaque(false);
			bookLabel.setEditable(false);
			bookLabel.setFocusable(false);

			//GBCs
			GridBagConstraints gbcBookLabel = new GridBagConstraints();
			gbcBookLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcBookLabel.gridx = 1;
			gbcBookLabel.gridy = 0;
			gbcBookLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcBookLabel.insets = new Insets(0, 16, 0, 0); // Add space between components

			//Empty Space
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(C_ListBG);
			GridBagConstraints gbcEmptyPanel = new GridBagConstraints();
			gbcEmptyPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcEmptyPanel.weightx = 1.0;
			gbcEmptyPanel.gridx = 2;
			gbcEmptyPanel.gridy = 0;

			//Buttons Action
			ListItemPanel.add(imageLabel, gbcImageLabel);
			ListItemPanel.add(bookLabel, gbcBookLabel);
			ListItemPanel.add(emptyPanel, gbcEmptyPanel);
			containerPanel.add(ListItemPanel);
			orderFound = true;
		}
		if (!orderFound) {
			containerPanel.removeAll();
			JPanel emptyPanel = new JPanel();
			emptyPanel.setLayout(new GridBagLayout());
			emptyPanel.setBackground(C_ListBG);

			JLabel emptyLabel = new JLabel("You have not made any Orders yet.");
			emptyLabel.setFont(new Font("Arial", Font.BOLD, 30));

			GridBagConstraints gbcEmptyLabel = new GridBagConstraints();
			gbcEmptyLabel.gridx = 0;
			gbcEmptyLabel.gridy = 0;

			emptyPanel.add(emptyLabel, gbcEmptyLabel);
			containerPanel.add(emptyPanel);
		}
	}

	private void updateTransactionHistory() {
		boolean transFound = false;
		containerPanel.removeAll();

		for (Transaction transaction : customer.getBorrowHistory()) {

			//Main Panel
			JPanel ListItemPanel = new JPanel();
			ListItemPanel.setLayout(new GridBagLayout());
			ListItemPanel.setBackground(C_ListBG);
			ListItemPanel.setPreferredSize(new Dimension(1280, panelHeight));
			ListItemPanel.setBorder(border);

			//Book Image
			ImageIcon userImage = new ImageIcon(FileSystemManager.cwd + "order.png");
			Image scaledImage = userImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust size
			ImageIcon scaledBookImage = new ImageIcon(scaledImage);
			JLabel imageLabel = new JLabel(scaledBookImage);

			//Book Properties
			imageLabel.setHorizontalTextPosition(JLabel.RIGHT);
			imageLabel.setVerticalTextPosition(JLabel.CENTER);

			//imageLabel.setPreferredSize(new Dimension());
			imageLabel.setIconTextGap(15);

			//GBCs
			GridBagConstraints gbcImageLabel = new GridBagConstraints();
			gbcImageLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcImageLabel.gridx = 0;
			gbcImageLabel.gridy = 0;
			gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcImageLabel.insets = new Insets(0, 48, 0, 0); // Add space between components

			//Book Text
			JTextArea bookLabel = new JTextArea(2, 20);
			bookLabel.setText(String.format(
					"ID: %s\n\nTitle: %s\n\nBorrow Date: %s\n\nReturn Date: %s",
					transaction.getID(),
					transaction.getBook().getTitle(),
					transaction.getBorrowDate(),
					transaction.getReturnDate()
			));
			bookLabel.setFont(new Font("Arial", Font.BOLD, 22));
			bookLabel.setWrapStyleWord(true);
			bookLabel.setLineWrap(true);
			bookLabel.setOpaque(false);
			bookLabel.setEditable(false);
			bookLabel.setFocusable(false);

			//GBCs
			GridBagConstraints gbcBookLabel = new GridBagConstraints();
			gbcBookLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcBookLabel.gridx = 1;
			gbcBookLabel.gridy = 0;
			gbcBookLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcBookLabel.insets = new Insets(0, 16, 0, 0); // Add space between components

			//Empty Space
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(C_ListBG);
			GridBagConstraints gbcEmptyPanel = new GridBagConstraints();
			gbcEmptyPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcEmptyPanel.weightx = 1.0;
			gbcEmptyPanel.gridx = 2;
			gbcEmptyPanel.gridy = 0;

			if (!transaction.isReturned()) {
				JButton returnButton = new JButton("Return Book");
				returnButton.setBackground(C_ButtonBG);
				returnButton.setForeground(Color.WHITE);
				returnButton.setPreferredSize(new Dimension(120, 40));
				returnButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (transaction.overDue()) {
							Object[] options = { "Cash", "Credit Card" };
							String message = String.format("Overdue fine found: $%.2f\nHow would you like to pay ?", transaction.getFine());
							int paymentMethod =
									JOptionPane.showOptionDialog(
											null,
											message,
											"Overdue Fine Payment Method",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null,         // Use default icon
											options,      // Buttons
											options[0]
									);  // Default button (Cash)
							JOptionPane.showMessageDialog(null, "Fine payment complete.");
						}
						transaction.setReturned(true);
						if (!customer.hasBorrows()) {
							customer = Admin.convertToCustomer(customer);
						}
						updateTransactionHistory();
					}
				});

				// GBCs for the button
				GridBagConstraints gbcReturnButton = new GridBagConstraints();
				gbcReturnButton.fill = GridBagConstraints.HORIZONTAL;
				gbcReturnButton.gridx = 3;
				gbcReturnButton.gridy = 0;
				gbcReturnButton.insets = new Insets(0, 0, 0, 70);

				// Add the button to the panel
				ListItemPanel.add(returnButton, gbcReturnButton);
			}

			//Buttons Action
			ListItemPanel.add(imageLabel, gbcImageLabel);
			ListItemPanel.add(bookLabel, gbcBookLabel);
			ListItemPanel.add(emptyPanel, gbcEmptyPanel);
			containerPanel.add(ListItemPanel);
			transFound = true;
		}
		if (!transFound) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setLayout(new GridBagLayout());
			emptyPanel.setBackground(C_ListBG);

			JLabel emptyLabel = new JLabel("Your Borrow history is empty.");
			emptyLabel.setFont(new Font("Arial", Font.BOLD, 30));

			GridBagConstraints gbcEmptyLabel = new GridBagConstraints();
			gbcEmptyLabel.gridx = 0;
			gbcEmptyLabel.gridy = 0;

			emptyPanel.add(emptyLabel, gbcEmptyLabel);
			containerPanel.add(emptyPanel);
		}
		containerPanel.revalidate();
		containerPanel.repaint();
	}
}

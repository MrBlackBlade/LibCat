package libcat;

import libcat.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminFrame
		extends JFrame
		implements FrameEnvironment {

	private JScrollPane scrollPane;
	private AdminFrame selfReference;
	protected JButton searchButton;

	private enum RadioSelect {
		ONE,
		TWO,
		THREE,
		FOUR,
		FIVE,
		RADIO_SELECT_MAX,
	}

	static RadioSelect choice = RadioSelect.ONE;
	static int panelHeight = 420;
	static JPanel containerPanel = new JPanel();
	static Border border = BorderFactory.createLineBorder(Color.black, 3);

	static Boolean searchForBooks = true;

	private void searchForBooks(String searchParameter) {
		boolean bookFound = false;
		ArrayList<Book> queryResult = new ArrayList<Book>();

		switch (choice) {
			case ONE ->
					queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.TITLE, searchParameter);
			case TWO ->
					queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.AUTHOR, searchParameter);
			case THREE ->
					queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.GENRE, searchParameter);
		}

		for (Book book : queryResult) {

			//Main Panel
			JPanel ListItemPanel = new JPanel();
			ListItemPanel.setLayout(new GridBagLayout());
			ListItemPanel.setBackground(C_ListBG);
			ListItemPanel.setPreferredSize(new Dimension(1280, panelHeight));
			ListItemPanel.setBorder(border);

			//Book Image
			ImageIcon bookImage = book.getImageIcon();
			Image scaledImage = bookImage.getImage().getScaledInstance(150, 225, Image.SCALE_SMOOTH); // Adjust size
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
					"Title: %s\n\nAuthor: %s\n\nGenre: %s\n\nPurchase Status: %s\n\nBorrow Status: %s",
					book.getTitle(),
					book.getAuthor(),
					book.getGenre(),
					String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
					String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE))
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

			JButton purchaseStatusButton = new JButton("Change Purchase Status");
			purchaseStatusButton.setBackground(C_ButtonBG);
			purchaseStatusButton.setForeground(Color.WHITE);

			JButton borrowStatusButton = new JButton("Change Borrow Status");
			borrowStatusButton.setBackground(C_ButtonBG);
			borrowStatusButton.setForeground(Color.WHITE);

			JButton editBookButton = new JButton("Edit Book");
			editBookButton.setBackground(C_ButtonBG);
			editBookButton.setForeground(Color.WHITE);

			purchaseStatusButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
						book.setPurchasable(false);

						bookLabel.setText(String.format(
								"Title: %s\n\nAuthor: %s\n\nGenre: %s\n\nPurchase Status: %s\n\nBorrow Status: %s",
								book.getTitle(),
								book.getAuthor(),
								book.getGenre(),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE))
						));

						System.out.println(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE));

					} else {
						book.setPurchasable(true);

						bookLabel.setText(String.format(
								"Title: %s\n\nAuthor: %s\n\nGenre: %s\n\nPurchase Status: %s\n\nBorrow Status: %s",
								book.getTitle(),
								book.getAuthor(),
								book.getGenre(),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE))
						));

						System.out.println(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE));
					}

				}
			});
			borrowStatusButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
						book.setBorrowable(false);

						bookLabel.setText(String.format(
								"Title: %s\n\nAuthor: %s\n\nGenre: %s\n\nPurchase Status: %s\n\nBorrow Status: %s",
								book.getTitle(),
								book.getAuthor(),
								book.getGenre(),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE))
						));

						System.out.println(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE));
					} else {
						book.setBorrowable(true);
						bookLabel.setText(String.format(
								"Title: %s\n\nAuthor: %s\n\nGenre: %s\n\nPurchase Status: %s\n\nBorrow Status: %s",
								book.getTitle(),
								book.getAuthor(),
								book.getGenre(),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
								String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE))
						));

						System.out.println(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE));
					}
				}
			});
			editBookButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					new EditFrame(book, selfReference);

				}
			});

			// Add buttons to a panel
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(3, 1, 5, 15)); // Adjust layout as needed
			buttonPanel.setPreferredSize(new Dimension(300, 1)); // Adjust size
			buttonPanel.setBackground(C_ListBG);
			buttonPanel.add(purchaseStatusButton);
			buttonPanel.add(borrowStatusButton);
			buttonPanel.add(editBookButton);

			//GBCs
			GridBagConstraints gbcButtonPanel = new GridBagConstraints();
			gbcButtonPanel.fill = GridBagConstraints.VERTICAL;
			gbcButtonPanel.gridx = 3; // Adjust column index as needed
			gbcButtonPanel.gridy = 0;
			gbcButtonPanel.anchor = GridBagConstraints.EAST; // Align to the right
			gbcButtonPanel.insets = new Insets(0, 0, 0, 50); // Add space between components

			// Add buttons to the ListItemPanel
			ListItemPanel.add(imageLabel, gbcImageLabel);
			ListItemPanel.add(bookLabel, gbcBookLabel);
			ListItemPanel.add(emptyPanel, gbcEmptyPanel);
			ListItemPanel.add(buttonPanel, gbcButtonPanel); // Add the button panel

			containerPanel.add(ListItemPanel);
			bookFound = true;
		}
		if (!bookFound) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setLayout(new GridBagLayout());
			emptyPanel.setBackground(C_ListBG);

			JLabel emptyLabel = new JLabel("Couldn't find what you are searching for :(");
			emptyLabel.setFont(new Font("Arial", Font.BOLD, 30));

			GridBagConstraints gbcEmptyLabel = new GridBagConstraints();
			gbcEmptyLabel.gridx = 0;
			gbcEmptyLabel.gridy = 0;

			emptyPanel.add(emptyLabel, gbcEmptyLabel);
			containerPanel.add(emptyPanel);
		}
		SwingUtilities.invokeLater(() -> {
			setLocationRelativeTo(null);
			setVisible(true);
			scrollPane.getVerticalScrollBar().setValue(0);
		});
	}

	private void searchForUsers(String searchParameter) {
		boolean usersFound = false;
		ArrayList<User> queryResult = new ArrayList<User>();

		switch (choice) {
			case FOUR ->
					queryResult = Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.NAME_LIKE, searchParameter);
			case FIVE ->
					queryResult = Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, searchParameter);

		}

		for (User user : queryResult) {
			//Main Panel
			JPanel ListItemPanel = new JPanel();
			ListItemPanel.setLayout(new GridBagLayout());
			ListItemPanel.setBackground(C_ListBG);
			ListItemPanel.setPreferredSize(new Dimension(1280, panelHeight));
			ListItemPanel.setBorder(border);

			//Book Image
			ImageIcon userImage = new ImageIcon(FileSystemManager.cwd + "userProfile.png");
			Image scaledImage = userImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Adjust size
			ImageIcon scaledBookImage = new ImageIcon(scaledImage);
			JLabel imageLabel = new JLabel(scaledBookImage);

			//Book Properties
			imageLabel.setHorizontalTextPosition(JLabel.RIGHT);
			imageLabel.setVerticalTextPosition(JLabel.CENTER);
			imageLabel.setIconTextGap(15);

			//GBCs
			GridBagConstraints gbcImageLabel = new GridBagConstraints();
			gbcImageLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcImageLabel.gridx = 0;
			gbcImageLabel.gridy = 0;
			gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcImageLabel.insets = new Insets(0, 48, 0, 0); // Add space between components

			//Book Text
			JTextArea userLabel = new JTextArea(2, 20);
			userLabel.setText(String.format(
					"Username: %s\n\nUser Type: %s\n\nID: %s",
					user.getName(),
					user.getType(),
					user.getID()
			));
			userLabel.setFont(new Font("Arial", Font.BOLD, 25));
			userLabel.setWrapStyleWord(true);
			userLabel.setLineWrap(true);
			userLabel.setOpaque(false);
			userLabel.setEditable(false);
			userLabel.setFocusable(false);

			//GBCs
			GridBagConstraints gbcUserLabel = new GridBagConstraints();
			gbcUserLabel.fill = GridBagConstraints.HORIZONTAL;
			gbcUserLabel.gridx = 1;
			gbcUserLabel.gridy = 0;
			gbcUserLabel.anchor = GridBagConstraints.WEST; // Align to the left
			gbcUserLabel.insets = new Insets(0, 16, 0, 0); // Add space between components

			//Empty Space
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(C_ListBG);
			GridBagConstraints gbcEmptyPanel = new GridBagConstraints();
			gbcEmptyPanel.fill = GridBagConstraints.HORIZONTAL;
			gbcEmptyPanel.weightx = 1.0;
			gbcEmptyPanel.gridx = 2;
			gbcEmptyPanel.gridy = 0;

			if (user.getType().equalsIgnoreCase("borrower")) {
				JButton updateButton = new JButton("Update");
				updateButton.setFont(new Font("Arial", Font.PLAIN, 18));
				updateButton.setBackground(C_ButtonBG);
				updateButton.setForeground(Color.WHITE);
				updateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Logic will be here

					}
				});

				JButton deleteButton = new JButton("Delete");
				deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
				deleteButton.setBackground(C_ButtonBG);
				deleteButton.setForeground(Color.WHITE);

				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
																   "Confirmation", JOptionPane.YES_NO_OPTION
						);

						if (option == JOptionPane.YES_OPTION) {
							Borrower borrower = (Borrower) ((Customer) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(user.getID())).get(0));
							Admin.deleteBorrower(borrower);

						}
					}
				});

				// GBCs for buttons
				GridBagConstraints gbcUpdateButton = new GridBagConstraints();
				gbcUpdateButton.gridx = 3;
				gbcUpdateButton.gridy = 0;
				gbcUpdateButton.anchor = GridBagConstraints.EAST; // Align to the right
				gbcUpdateButton.insets = new Insets(0, 0, 0, 60); // Add space between components

				GridBagConstraints gbcDeleteButton = new GridBagConstraints();
				gbcDeleteButton.gridx = 4;
				gbcDeleteButton.gridy = 0;
				gbcDeleteButton.anchor = GridBagConstraints.EAST; // Align to the right
				gbcDeleteButton.insets = new Insets(0, 0, 0, 60); // Add space between components

				// Add buttons to the panel
				ListItemPanel.add(updateButton, gbcUpdateButton);
				ListItemPanel.add(deleteButton, gbcDeleteButton);

			}
			ListItemPanel.add(imageLabel, gbcImageLabel);
			ListItemPanel.add(userLabel, gbcUserLabel);
			ListItemPanel.add(emptyPanel, gbcEmptyPanel);
			containerPanel.add(ListItemPanel);
			usersFound = true;
		}
		if (!usersFound) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setLayout(new GridBagLayout());
			emptyPanel.setBackground(C_ListBG);

			JLabel emptyLabel = new JLabel("Couldn't find what you are searching for :(");
			emptyLabel.setFont(new Font("Arial", Font.BOLD, 30));

			GridBagConstraints gbcEmptyLabel = new GridBagConstraints();
			gbcEmptyLabel.gridx = 0;
			gbcEmptyLabel.gridy = 0;

			emptyPanel.add(emptyLabel, gbcEmptyLabel);
			containerPanel.add(emptyPanel);
		}

	}

	public AdminFrame() {
		selfReference = this;
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		// Window Size, Icon and Name
		ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JButton booksSwitch = new JButton("Books");
		booksSwitch.setBackground(C_ButtonBG);
		booksSwitch.setForeground(Color.WHITE);

		JButton addBook = new JButton("Add Book");
		addBook.setBackground(C_ButtonBG);
		addBook.setForeground(Color.WHITE);

		JButton usersSwitch = new JButton("Users");
		usersSwitch.setBackground(C_ButtonBG);
		usersSwitch.setForeground(Color.WHITE);

		topPanel.add(booksSwitch);
		topPanel.add(usersSwitch);
		topPanel.add(addBook);

		layeredPane.add(topPanel, JLayeredPane.PALETTE_LAYER);

		// Welcome Panel
		JPanel welcomePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// welcomePanel.setPreferredSize(new Dimension(1280, 150));
		welcomePanel.setBackground(C_WelcomeBG);
		welcomePanel.setBounds(0, 0, 1280, 150);

		JLabel welcomeLabel = new JLabel("Welcome Admin");
		welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setVerticalAlignment(JLabel.TOP);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));

		JTextField searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(250, 30));
		searchBar.setFont(new Font("Arial", Font.PLAIN, 25));

		searchButton = new JButton("Search");
		searchButton.setBackground(C_ButtonBG);
		searchButton.setForeground(Color.WHITE);

		// Add radio buttons
		JRadioButton radioButton1 = new JRadioButton("Title");
		radioButton1.setBackground(C_WelcomeBG);

		JRadioButton radioButton2 = new JRadioButton("Author");
		radioButton2.setBackground(C_WelcomeBG);

		JRadioButton radioButton3 = new JRadioButton("Genre");
		radioButton3.setBackground(C_WelcomeBG);

		JRadioButton radioButton4 = new JRadioButton("Username");
		radioButton4.setBackground(C_WelcomeBG);

		JRadioButton radioButton5 = new JRadioButton("ID");
		radioButton5.setBackground(C_WelcomeBG);

		// Group the radio buttons so that only one can be selected at a time
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(radioButton1);
		radioButtonGroup.add(radioButton2);
		radioButtonGroup.add(radioButton3);
		radioButtonGroup.add(radioButton4);
		radioButtonGroup.add(radioButton5);

		// Perform the action based on the selected radio button
		ActionListener radioListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (radioButton1.isSelected()) {
					choice = RadioSelect.ONE;
				} else if (radioButton2.isSelected()) {
					choice = RadioSelect.TWO;
				} else if (radioButton3.isSelected()) {
					choice = RadioSelect.THREE;
				} else if (radioButton4.isSelected()) {
					choice = RadioSelect.FOUR;
				} else if (radioButton5.isSelected()) {
					choice = RadioSelect.FIVE;
				}
			}
		};

		radioButton1.addActionListener(radioListener);
		radioButton2.addActionListener(radioListener);
		radioButton3.addActionListener(radioListener);
		radioButton4.addActionListener(radioListener);
		radioButton5.addActionListener(radioListener);

		// Components placing
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0); // Add some space between components
		welcomePanel.add(welcomeLabel, gbc);

		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		searchPanel.add(searchBar);
		searchPanel.add(searchButton);

		gbc.gridy = 1;
		welcomePanel.add(searchPanel, gbc);

		JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		radioPanel.setBorder(new LineBorder(Color.WHITE, 3));
		gbc.gridy = 2;
		gbc.gridwidth = 2; // Span two columns

		welcomePanel.add(radioPanel, gbc);

		layeredPane.add(welcomePanel, JLayeredPane.DEFAULT_LAYER);

		// Book Panels
		scrollPane = new JScrollPane(containerPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		usersSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				searchForBooks = false;

				searchBar.setText("");

				radioPanel.removeAll();
				radioPanel.add(radioButton4);
				radioPanel.add(radioButton5);

				radioButton4.doClick();

				radioPanel.revalidate();
				radioPanel.repaint();

				searchButton.doClick();

				SwingUtilities.invokeLater(() -> {
					setLocationRelativeTo(null);
					setVisible(true);
					scrollPane.getVerticalScrollBar().setValue(0);
				});
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});
		booksSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				searchForBooks = true;

				searchBar.setText("");

				radioPanel.removeAll();
				radioPanel.add(radioButton1);
				radioPanel.add(radioButton2);
				radioPanel.add(radioButton3);

				radioButton1.doClick();

				radioPanel.revalidate();
				radioPanel.repaint();

				searchButton.doClick();

				SwingUtilities.invokeLater(() -> {
					setLocationRelativeTo(null);
					setVisible(true);
					scrollPane.getVerticalScrollBar().setValue(0);
				});
			}
		});
		addBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewBookFrame(selfReference);
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containerPanel.removeAll();
				if (searchForBooks) {
					searchForBooks(searchBar.getText());
				} else {
					searchForUsers(searchBar.getText());
				}
				scrollPane.revalidate();
				scrollPane.repaint();

				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});

		// Add components
		add(layeredPane, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		booksSwitch.doClick();
		searchButton.doClick();
		SwingUtilities.invokeLater(() -> {
			setLocationRelativeTo(null);
			setVisible(true);
			scrollPane.getVerticalScrollBar().setValue(0);
		});

	}

}

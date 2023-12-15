package libcat;

import libcat.util.Book;
import libcat.util.Rating;
import libcat.util.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements FrameEnvironment{

    private enum RadioSelect {
        ONE,
        TWO,
        THREE,
        RADIO_SELECT_MAX,
    }

    static RadioSelect choice = RadioSelect.ONE;

    public MainFrame(User user) {
        // Window Size, Icon and Name
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900);
        setResizable(false);
        setTitle("LibCat");
        setIconImage(icon.getImage());
        getContentPane().setBackground(C_ListBG);

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        welcomePanel.setPreferredSize(new Dimension(1280, 150));
        welcomePanel.setBackground(C_WelcomeBG);

        JLabel welcomeLabel = new JLabel("Welcome " + user.getName());
        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.TOP);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));

        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(250, 30));
        searchBar.setFont(new Font("Arial", Font.PLAIN, 25));

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(C_ButtonBG);
        searchButton.setForeground(Color.WHITE);

        // Add radio buttons
        JRadioButton radioButton1 = new JRadioButton("Title");
        radioButton1.setBackground(C_WelcomeBG);

        JRadioButton radioButton2 = new JRadioButton("Author");
        radioButton2.setBackground(C_WelcomeBG);

        JRadioButton radioButton3 = new JRadioButton("Genre");
        radioButton3.setBackground(C_WelcomeBG);

        // Group the radio buttons so that only one can be selected at a time
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(radioButton1);
        radioButtonGroup.add(radioButton2);
        radioButtonGroup.add(radioButton3);

        // To display the radio button being selected by default
        radioButton1.setSelected(true);

        // Perform the action based on the selected radio button
        ActionListener radioListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (radioButton1.isSelected()) {
                    choice = RadioSelect.ONE;
                } else if (radioButton2.isSelected()) {
                    choice = RadioSelect.TWO;
                } else if (radioButton3.isSelected()) {
                    choice = RadioSelect.THREE;
                }
            }
        };

        radioButton1.addActionListener(radioListener);
        radioButton2.addActionListener(radioListener);
        radioButton3.addActionListener(radioListener);

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
        radioPanel.add(radioButton1);
        radioPanel.add(radioButton2);
        radioPanel.add(radioButton3);
        radioPanel.setBorder(new LineBorder(Color.WHITE, 3));

        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span two columns
        welcomePanel.add(radioPanel, gbc);

        // Book Panels
        Border border = BorderFactory.createLineBorder(Color.black, 3);
        int panelHeight = 420;

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                containerPanel.removeAll();
                boolean bookFound = false;

                String searchResult = searchBar.getText();
                ArrayList<Book> queryResult = new ArrayList<Book>();

                switch (choice) {
                    case ONE ->
                            queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.TITLE, searchResult);
                    case TWO ->
                            queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.AUTHOR, searchResult);
                    case THREE ->
                            queryResult = Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.GENRE, searchResult);
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
                    bookLabel.setText(String.format("Title: %s\n\nAuthor: %s\n\nGenre: %s",
                            book.getBookTitle(),
                            book.getAuthor(),
                            book.getGenre()));
                    bookLabel.setFont(new Font("Arial", Font.BOLD, 25));
                    bookLabel.setWrapStyleWord(true);
                    bookLabel.setLineWrap(true);
                    bookLabel.setOpaque(false);
                    bookLabel.setEditable(false);
                    bookLabel.setFocusable(false);

                    //GBCs
                    GridBagConstraints gbcBookLabel = new GridBagConstraints();
                    gbcBookLabel.fill = GridBagConstraints.HORIZONTAL;
                    //gbcImageLabel.weightx = 1.0;
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

                    JPanel rightPanel = new JPanel(new BorderLayout());
                    rightPanel.setBackground(Color.black);
                    rightPanel.setPreferredSize(new Dimension(400, panelHeight - 15));

                    GridBagConstraints gbcRightPanel = new GridBagConstraints();
                    gbcRightPanel.fill = GridBagConstraints.HORIZONTAL;
                    gbcRightPanel.gridx = 3;
                    gbcRightPanel.gridy = 0;
                    gbcRightPanel.anchor = GridBagConstraints.EAST; // Align to the left
                    gbcRightPanel.insets = new Insets(0, 0, 0, 48);

                    JTextArea reviewField = new JTextArea();
                    reviewField.setFont(new Font("Arial", Font.BOLD, 16));
                    reviewField.setLineWrap(true);
                    reviewField.setWrapStyleWord(true);
                    reviewField.setMargin(new Insets(4, 4, 4, 4));
                    reviewField.setEditable(false);

                    // Set scroll bar for the reviews
                    JScrollPane reviewScroll = new JScrollPane(reviewField);
                    reviewScroll.setPreferredSize(new Dimension(300, 350));
                    reviewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    reviewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    // Read content from a text file and set it in the text field
                    // A txt file will have all the filePaths of every review and will loop on that file
                    StringBuilder reviews = new StringBuilder();
                    for (Rating rating : book.getRatings()) {
                        String isLike = rating.isLike() ? " likes:\n" : " dislikes:\n";
                        reviews.append(rating.getUsername()).append(isLike).append(rating.getReview()).append("\n\n");
                    }
                    reviewField.setText(reviews.toString().trim());

                    JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
                    JButton buyButton = new JButton("Buy");
                    JButton borrowButton = new JButton("Borrow");
                    JButton likeButton = new JButton("Like");
                    JButton dislikeButton = new JButton("Dislike");

                    buyButton.setBackground(C_ButtonBG);
                    buyButton.setForeground(Color.WHITE);
                    borrowButton.setBackground(C_ButtonBG);
                    borrowButton.setForeground(Color.WHITE);
                    likeButton.setBackground(C_ButtonBG);
                    likeButton.setForeground(Color.WHITE);
                    dislikeButton.setBackground(C_ButtonBG);
                    dislikeButton.setForeground(Color.WHITE);

                    //Buttons Action
                    buyButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Logic will be here
                            JOptionPane.showMessageDialog(null, "Buy");
                        }
                    });
                    borrowButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Logic will be here
                            JOptionPane.showMessageDialog(null, "Borrowed");
                        }
                    });
                    likeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            /// Logic will be here
                            JOptionPane.showMessageDialog(null, "Like");
                        }
                    });
                    dislikeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Logic will be here
                            JOptionPane.showMessageDialog(null, "Dislike");
                        }
                    });

                    buttonPanel.add(likeButton);
                    buttonPanel.add(dislikeButton);
                    buttonPanel.add(buyButton);
                    buttonPanel.add(borrowButton);

                    rightPanel.add(reviewScroll, BorderLayout.NORTH); // Place the text field at the top
                    rightPanel.add(buttonPanel, BorderLayout.CENTER); // Place the button panel in the center

                    ListItemPanel.add(imageLabel, gbcImageLabel);
                    ListItemPanel.add(bookLabel, gbcBookLabel);
                    ListItemPanel.add(emptyPanel, gbcEmptyPanel);
                    ListItemPanel.add(rightPanel, gbcRightPanel);
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
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        });

        // Add components
        add(welcomePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.doClick();
        SwingUtilities.invokeLater(() -> {
            setLocationRelativeTo(null);
            setVisible(true);
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}

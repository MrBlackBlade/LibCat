package libcat.util.Login;

import libcat.util.FileSystemManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    public MainFrame(String username) {

        // Window Size, Icon and Name
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900);
        setResizable(false);
        setTitle("LibCat");
        setIconImage(icon.getImage());
        getContentPane().setBackground(new Color(242, 231, 199));

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        welcomePanel.setPreferredSize(new Dimension(1280, 150));
        welcomePanel.setBackground(new Color(200, 200, 200));

        JLabel welcomeLabel = new JLabel("Welcome " + username);
        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.TOP);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));

        JTextField searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(250, 30));
        searchBar.setFont(new Font("Arial", Font.TRUETYPE_FONT, 25));

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);

        // Add components with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); // Add some space between components
        welcomePanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        welcomePanel.add(searchBar, gbc);

        gbc.gridy = 2;
        welcomePanel.add(searchButton, gbc);

        // Book Panels
        Border border = BorderFactory.createLineBorder(Color.black, 3);
        int panelHeight = 420;

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        ArrayList<JPanel> bookList = new ArrayList<>();
        int booksNumber = 10;

        for (int i = 0; i < booksNumber ; i++) {
            JPanel book = new JPanel();
            book.setLayout(new GridBagLayout());
            book.setBackground(new Color(242, 231, 199));
            book.setPreferredSize(new Dimension(1280, panelHeight));
            book.setBorder(border);

            // Add an image for each book by using a txt file that iterates through lines for every image path for each book ( Book order is very important )
            // Art of war for now

            ImageIcon bookImage = new ImageIcon(FileSystemManager.cwd + "ArtOfWar.jpg"); // Specify the path to your image
            Image scaledImage = bookImage.getImage().getScaledInstance(150, 225, Image.SCALE_SMOOTH); // Adjust size
            ImageIcon scaledBookImage = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(scaledBookImage);
            imageLabel.setText("<html>Title: Art of War " + (i + 1) + "<br>" + "<br>" + "Author: Sun Tzu" + "<br>" + "<br>" + "Genre: Non-Fiction");
            imageLabel.setHorizontalTextPosition(JLabel.CENTER);
            imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
            imageLabel.setFont(new Font("Arial", Font.BOLD, 25));
            imageLabel.setIconTextGap(15);

            GridBagConstraints gbcImageLabel = new GridBagConstraints();
            gbcImageLabel.gridx = 0;
            gbcImageLabel.gridy = 0;
            gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
            gbcImageLabel.insets = new Insets(0, 0, 0, 590); // Add space between components

            // Add information about each book by using a txt file that iterates through a specific number of lines for each book ( Book order is very important )
            // just "Art Of War" for now

            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.setBackground(Color.black);
            rightPanel.setPreferredSize(new Dimension(400, panelHeight - 15));

            GridBagConstraints gbcRightPanel = new GridBagConstraints();
            gbcRightPanel.gridx = 1;
            gbcRightPanel.gridy = 0;
            gbcRightPanel.anchor = GridBagConstraints.WEST; // Align to the left

            JTextArea reviewField = new JTextArea();
            reviewField.setFont(new Font("Arial", Font.BOLD, 16));
            reviewField.setLineWrap(true);
            reviewField.setWrapStyleWord(true);

// Set scroll bar for the reviews
            JScrollPane reviewScroll = new JScrollPane(reviewField);
            reviewScroll.setPreferredSize(new Dimension(300, 350));
            reviewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            reviewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


            // Read content from a text file and set it in the text field
            // A txt file will have all the filePaths of every review and will loop on that file
            String reviewPath = FileSystemManager.cwd + "ArtOfWar_reviews.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(reviewPath))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reviewField.setText(content.toString().trim());
            } catch (IOException e) {
                e.printStackTrace();
            }

            JPanel buttonPanel = new JPanel(new GridLayout(1, 4)); // GridLayout with 1 row and 4 columns for buttons
            JButton buyButton = new JButton("Buy");
            JButton borrowButton = new JButton("Borrow");
            JButton likeButton = new JButton("Like");
            JButton dislikeButton = new JButton("Dislike");

            buyButton.setBackground(new Color(70, 130, 180));
            buyButton.setForeground(Color.WHITE);
            borrowButton.setBackground(new Color(70, 130, 180));
            borrowButton.setForeground(Color.WHITE);
            likeButton.setBackground(new Color(70, 130, 180));
            likeButton.setForeground(Color.WHITE);
            dislikeButton.setBackground(new Color(70, 130, 180));
            dislikeButton.setForeground(Color.WHITE);

            //Buttons Action
            buyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchResult = searchBar.getText();
                    // Logic will be here
                    JOptionPane.showMessageDialog(null, "Buy");
                }
            });
            borrowButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchResult = searchBar.getText();
                    // Logic will be here
                    JOptionPane.showMessageDialog(null, "Borrowed");
                }
            });
            likeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchResult = searchBar.getText();
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

            book.add(imageLabel, gbcImageLabel);
            book.add(rightPanel, gbcRightPanel);
            containerPanel.add(book);
            bookList.add(book);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //Search Button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchResult = searchBar.getText();
                // Search Logic will be here
                JOptionPane.showMessageDialog(null, searchResult);
            }
        });

        // Add components
        add(welcomePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        //add(containerPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

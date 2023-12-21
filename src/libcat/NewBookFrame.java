package libcat;

import libcat.util.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewBookFrame extends JFrame {
    private final JTextField bookTitle;
    private final JTextField authorName;

    private final AdminFrame adminFrameReference;
    private final JTextField newYear;
    private final JTextField newGenre;
    private final JTextField bookPrice;
    private final JTextField salePercent;
    public NewBookFrame(Book book,AdminFrame adminFrameReference) {

        this.adminFrameReference = adminFrameReference;

        setTitle("Register Form");
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setPreferredSize(new Dimension(500, 500));
        setLayout(new GridBagLayout());

        Insets insets = new Insets(0,5,2,5);

        // Components
        JLabel bookTitleLabel = new JLabel("Title:");
        GridBagConstraints gbcTitleLabel = new GridBagConstraints();
        gbcTitleLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcTitleLabel.gridy = 0;
        gbcTitleLabel.gridx = 0;
        gbcTitleLabel.insets = insets;

        bookTitle = new JTextField(20);
        GridBagConstraints gbcTitleText = new GridBagConstraints();
        gbcTitleText.fill = GridBagConstraints.HORIZONTAL;
        gbcTitleText.gridy = 1;
        gbcTitleText.gridx = 0;
        gbcTitleText.insets = insets;

        JLabel authorLabel = new JLabel("Author:");
        GridBagConstraints gbcAuthorLabel = new GridBagConstraints();
        gbcAuthorLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcAuthorLabel.gridy = 2;
        gbcAuthorLabel.gridx = 0;
        gbcAuthorLabel.insets = insets;

        authorName = new JTextField(20);
        GridBagConstraints gbcAuthorText = new GridBagConstraints();
        gbcAuthorText.fill = GridBagConstraints.HORIZONTAL;
        gbcAuthorText.gridy = 3;
        gbcAuthorText.gridx = 0;
        gbcAuthorText.insets = insets;

        JLabel genreLabel = new JLabel("Genre:");
        GridBagConstraints gbcGenreLabel = new GridBagConstraints();
        gbcGenreLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcGenreLabel.gridy = 4;
        gbcGenreLabel.gridx = 0;
        gbcGenreLabel.insets = insets;

        newGenre = new JTextField(20);
        GridBagConstraints gbcGenreText = new GridBagConstraints();
        gbcGenreText.fill = GridBagConstraints.HORIZONTAL;
        gbcGenreText.gridy = 5;
        gbcGenreText.gridx = 0;
        gbcGenreText.insets = insets;

        JLabel yearLabel = new JLabel("Publish Year:");
        GridBagConstraints gbcYearLabel = new GridBagConstraints();
        gbcYearLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcYearLabel.gridy = 6;
        gbcYearLabel.gridx = 0;
        gbcYearLabel.insets = insets;

        newYear = new JTextField(20);
        GridBagConstraints gbcYearText = new GridBagConstraints();
        gbcYearText.fill = GridBagConstraints.HORIZONTAL;
        gbcYearText.gridy = 7;
        gbcYearText.gridx = 0;
        gbcYearText.insets = insets;


        JLabel priceLabel = new JLabel("Price:");
        GridBagConstraints gbcPriceLabel = new GridBagConstraints();
        gbcPriceLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcPriceLabel.gridy = 8;
        gbcPriceLabel.gridx = 0;
        gbcPriceLabel.insets = insets;

        bookPrice = new JTextField(20);
        GridBagConstraints gbcPriceField = new GridBagConstraints();
        gbcPriceField.fill = GridBagConstraints.HORIZONTAL;
        gbcPriceField.gridy = 9;
        gbcPriceField.gridx = 0;
        gbcPriceField.insets = insets;

        JLabel saleLabel = new JLabel("Sale Percent:");
        GridBagConstraints gbcSaleLabel = new GridBagConstraints();
        gbcSaleLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcSaleLabel.gridy = 10;
        gbcSaleLabel.gridx = 0;
        gbcSaleLabel.insets = insets;

        salePercent = new JTextField(20);
        GridBagConstraints gbcSaleField = new GridBagConstraints();
        gbcSaleField.fill = GridBagConstraints.HORIZONTAL;
        gbcSaleField.gridy = 11;
        gbcSaleField.gridx = 0;
        gbcSaleField.insets = insets;

        JButton submitButton = new JButton("Submit");
        GridBagConstraints gbcRegisterButton = new GridBagConstraints();
        gbcRegisterButton.fill = GridBagConstraints.HORIZONTAL;
        gbcRegisterButton.gridy = 12;
        gbcRegisterButton.gridx = 0;
        gbcRegisterButton.insets = insets;

        add(bookTitleLabel, gbcTitleLabel);
        add(bookTitle,gbcTitleText);

        add(genreLabel, gbcGenreLabel);
        add(newGenre, gbcGenreText);

        add(authorLabel, gbcAuthorLabel);
        add(authorName, gbcAuthorText);

        add(yearLabel,gbcYearLabel);
        add(newYear,gbcYearText);

        add(priceLabel, gbcPriceLabel);
        add(bookPrice, gbcPriceField);

        add(saleLabel, gbcSaleLabel);
        add(salePercent, gbcSaleField);

        add(submitButton, gbcRegisterButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Admin.addBook(new Book(book.getID(),bookTitle.getText(),authorName.getText(),newGenre.getText(),newYear.getText(),Double.parseDouble(bookPrice.getText()),Double.parseDouble(salePercent.getText()));
                JOptionPane.showMessageDialog(null, "Book Updated Succecfully");
                adminFrameReference.searchButton.doClick();
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

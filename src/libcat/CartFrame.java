package libcat;

import libcat.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import static libcat.AdminFrame.border;

public class CartFrame extends JFrame implements FrameEnvironment {

    private JPanel containerPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(containerPanel);
    private Customer customer;

    public CartFrame(User user) {
        this.customer = (Customer) user;
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(780, 900);
        setResizable(false);
        setTitle("LibCat");
        setIconImage(icon.getImage());
        getContentPane().setBackground(C_ListBG);

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
    }

    protected void showCart() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        setVisible(true);
        updateCart();
    }

    protected void updateCart() {

        containerPanel.removeAll();

        JLabel purchaseListLabel = new JLabel("Purchase List");
        purchaseListLabel.setFont(new Font("Arial", Font.BOLD, 24));
        purchaseListLabel.setForeground(Color.BLACK);
        purchaseListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel purchaseLabelPanel = new JPanel();
        purchaseLabelPanel.setLayout(new BoxLayout(purchaseLabelPanel, BoxLayout.X_AXIS));
        purchaseLabelPanel.setBackground(C_WelcomeBG);
        purchaseLabelPanel.add(Box.createHorizontalGlue());
        purchaseLabelPanel.add(purchaseListLabel);
        purchaseLabelPanel.add(Box.createHorizontalGlue());

        containerPanel.add(purchaseLabelPanel);

        for (Order order : customer.getCart().getPendingOrders()) {


            JTextPane bookLabel = new JTextPane();
            bookLabel.setFont(new Font("Arial", Font.BOLD, 18));
            bookLabel.setOpaque(false);
            bookLabel.setEditable(false);
            bookLabel.setFocusable(false);

            //Main Panel
            JPanel ListItemPanel = new JPanel();
            ListItemPanel.setLayout(new GridBagLayout());
            ListItemPanel.setBackground(C_ListBG);
            ListItemPanel.setPreferredSize(new Dimension(700, 200));
            ListItemPanel.setBorder(border);

            //Book Image
            ImageIcon bookImage = order.getBook().getImageIcon();
            Image scaledImage = bookImage.getImage().getScaledInstance(94, 140, Image.SCALE_SMOOTH); // Adjust size
            ImageIcon scaledBookImage = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledBookImage);

            //Book Properties
            imageLabel.setHorizontalTextPosition(JLabel.RIGHT);
            imageLabel.setVerticalTextPosition(JLabel.CENTER);

            JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonContainerPanel.setOpaque(false);

            JButton addQuantity = new JButton("+");
            addQuantity.setBackground(C_ButtonBG);
            addQuantity.setForeground(Color.WHITE);
            addQuantity.setFont(new Font("Arial", Font.BOLD, 16));

            JButton decQuantity = new JButton("−");
            decQuantity.setBackground(C_ButtonBG);
            decQuantity.setForeground(Color.WHITE);
            decQuantity.setFont(new Font("Arial", Font.BOLD, 16));

            addQuantity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    customer.getCart().modifyPurchase(order, order.getQuantity() + 1);

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            order.getBook().getSalePrice(),
                            order.getQuantity()));
                    updateCart();
                    System.out.println(customer.getCart().getTotalPrice());
                }
            });
            decQuantity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (order.getQuantity() > 1) {

                        customer.getCart().modifyPurchase(order, (order.getQuantity() - 1));


                    } else {

                        customer.getCart().modifyPurchase(order, 0);
                        if (customer.getCart().getPendingOrders().isEmpty()) {

                            containerPanel.remove(purchaseLabelPanel);
                        }

                        containerPanel.remove(ListItemPanel);
                        containerPanel.revalidate();
                        containerPanel.repaint();

                    }

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            order.getBook().getSalePrice(),
                            order.getQuantity()));
                    updateCart();
                }
            });

            buttonContainerPanel.add(addQuantity);
            buttonContainerPanel.add(decQuantity);

            // GBCs for the buttonContainerPanel
            GridBagConstraints gbcButtonContainerPanel = new GridBagConstraints();
            gbcButtonContainerPanel.fill = GridBagConstraints.HORIZONTAL;
            gbcButtonContainerPanel.gridx = 2;  // Place it in the third column
            gbcButtonContainerPanel.gridy = 0;
            gbcButtonContainerPanel.anchor = GridBagConstraints.EAST;  // Align to the right

            imageLabel.setIconTextGap(15);

            //GBCs
            GridBagConstraints gbcImageLabel = new GridBagConstraints();
            gbcImageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbcImageLabel.gridx = 0;
            gbcImageLabel.gridy = 0;
            gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
            gbcImageLabel.insets = new Insets(0, 48, 0, 0); // Add space between components

            //Book Text
            bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                    order.getBook().getTitle(),
                    order.getBook().getSalePrice(),
                    order.getQuantity()));

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


            ListItemPanel.add(imageLabel, gbcImageLabel);
            ListItemPanel.add(bookLabel, gbcBookLabel);
            ListItemPanel.add(buttonContainerPanel, gbcButtonContainerPanel);
            ListItemPanel.add(emptyPanel, gbcEmptyPanel);
            containerPanel.add(ListItemPanel);
        }
        updateBorrowList();
        containerPanel.add(checkoutPanel());
        containerPanel.revalidate();
        containerPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    protected void updateBorrowList() {

        JLabel borrowListLabel = new JLabel("Borrow List");
        borrowListLabel.setFont(new Font("Arial", Font.BOLD, 24));
        borrowListLabel.setForeground(Color.BLACK);
        borrowListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel borrowLabelPanel = new JPanel();
        borrowLabelPanel.setLayout(new BoxLayout(borrowLabelPanel, BoxLayout.X_AXIS));
        borrowLabelPanel.setBackground(C_WelcomeBG);
        borrowLabelPanel.add(Box.createHorizontalGlue());
        borrowLabelPanel.add(borrowListLabel);
        borrowLabelPanel.add(Box.createHorizontalGlue());

        if (!customer.getCart().getPendingTransactions().isEmpty()) {

            containerPanel.add(borrowLabelPanel);
        } else {
            containerPanel.remove(borrowLabelPanel);
        }

        for (Transaction transaction : customer.getCart().getPendingTransactions()) {

            JTextPane bookLabel = new JTextPane();
            bookLabel.setFont(new Font("Arial", Font.BOLD, 18));
            bookLabel.setOpaque(false);
            bookLabel.setEditable(false);
            bookLabel.setFocusable(false);

            //Main Panel
            JPanel ListItemPanel = new JPanel();
            ListItemPanel.setLayout(new GridBagLayout());
            ListItemPanel.setBackground(C_ListBG);
            ListItemPanel.setPreferredSize(new Dimension(700, 200));
            ListItemPanel.setBorder(border);

            //Book Image
            ImageIcon bookImage = transaction.getBook().getImageIcon();
            Image scaledImage = bookImage.getImage().getScaledInstance(94, 140, Image.SCALE_SMOOTH); // Adjust size
            ImageIcon scaledBookImage = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledBookImage);

            //Book Properties
            imageLabel.setHorizontalTextPosition(JLabel.RIGHT);
            imageLabel.setVerticalTextPosition(JLabel.CENTER);

            JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonContainerPanel.setOpaque(false);

            JButton decQuantity = new JButton("−");
            decQuantity.setBackground(C_ButtonBG);
            decQuantity.setForeground(Color.WHITE);
            decQuantity.setFont(new Font("Arial", Font.BOLD, 16));

            decQuantity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    customer.getCart().deleteBorrow(transaction);

                    if (customer.getCart().getPendingTransactions().isEmpty()) {

                        containerPanel.remove(borrowLabelPanel);
                    }
                    containerPanel.remove(ListItemPanel);
                    containerPanel.revalidate();
                    containerPanel.repaint();
                    updateCart();
                }
            });
            buttonContainerPanel.add(decQuantity);

            // GBCs for the buttonContainerPanel
            GridBagConstraints gbcButtonContainerPanel = new GridBagConstraints();
            gbcButtonContainerPanel.fill = GridBagConstraints.HORIZONTAL;
            gbcButtonContainerPanel.gridx = 2;
            gbcButtonContainerPanel.gridy = 0;
            gbcButtonContainerPanel.anchor = GridBagConstraints.EAST;

            imageLabel.setIconTextGap(15);

            //GBCs
            GridBagConstraints gbcImageLabel = new GridBagConstraints();
            gbcImageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbcImageLabel.gridx = 0;
            gbcImageLabel.gridy = 0;
            gbcImageLabel.anchor = GridBagConstraints.WEST; // Align to the left
            gbcImageLabel.insets = new Insets(0, 48, 0, 0); // Add space between components

            bookLabel.setText(String.format("Title: %s\n\nBorrow Date: %s\n\nReturn Date: %s",
                    transaction.getBook().getTitle(),
                    transaction.getBorrowDate(),
                    transaction.getReturnDate()));

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

            ListItemPanel.add(imageLabel, gbcImageLabel);
            ListItemPanel.add(bookLabel, gbcBookLabel);
            ListItemPanel.add(buttonContainerPanel, gbcButtonContainerPanel);
            ListItemPanel.add(emptyPanel, gbcEmptyPanel);
            containerPanel.add(ListItemPanel);
        }
        containerPanel.revalidate();
        containerPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private JPanel checkoutPanel() {
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.X_AXIS));
        checkoutPanel.setBackground(C_ListBG);

        // Label for Total Price
        JLabel totalPriceLabel = new JLabel();
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalPriceLabel.setText(String.format("Total Price: %s", customer.getCart().getTotalPrice()));

        // Button for Checkout
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(C_ButtonBG);
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customer.getCart().checkout()) {
                    System.out.println("Checkout complete");
                    switch (customer.getType()) {
                        case "customer": {
                            System.out.println("This is a customer");
                            customer = Admin.convert(customer);
                            break;
                        }

                        case "borrower": {
                            System.out.println("This is a borrower");
                            break;
                        }
                    }

                    updateCart();
                    System.out.printf("The customer is now a %s", customer.getType() + "\n");
                }
            }
        });

        // Add components to the checkout panel
        checkoutPanel.add(Box.createHorizontalStrut(20)); // Add some space
        checkoutPanel.add(totalPriceLabel);
        checkoutPanel.add(Box.createHorizontalStrut(10)); // Add some space
        checkoutPanel.add(Box.createHorizontalGlue()); // Expand space between components
        checkoutPanel.add(checkoutButton);
        checkoutPanel.add(Box.createHorizontalStrut(20)); // Add some space

        return checkoutPanel;
    }
}

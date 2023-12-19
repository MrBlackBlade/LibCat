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
    protected void showCart(){
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

                    if (order.getBook().getSalePercent() > 0.0) {

                        order.setQuantity(order.getQuantity() + 1);

                    } else {
                        order.setQuantity(order.getQuantity() + 1);

                    }

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            order.getBook().getSalePrice(),
                            order.getQuantity()));

                }
            });
            decQuantity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (order.getQuantity() > 1) {

                        if (order.getBook().getSalePercent() > 0.0) {

                            order.setQuantity(order.getQuantity() - 1);

                        } else {
                            order.setQuantity(order.getQuantity() - 1);

                        }

                    } else {

                        customer.getCart().deletePurchase(order.getBook());

                        containerPanel.remove(ListItemPanel);
                        containerPanel.revalidate();
                        containerPanel.repaint();

                    }

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            order.getBook().getSalePrice(),
                            order.getQuantity()));

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
        containerPanel.revalidate();
        containerPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }
}

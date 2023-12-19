package libcat;

import libcat.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import static libcat.AdminFrame.border;
import static libcat.AdminFrame.panelHeight;

public class CartFrame extends JFrame implements FrameEnvironment {

    public CartFrame(User user) {
        // Window Size, Icon and Name
        ImageIcon icon = new ImageIcon(FileSystemManager.cwd + "LibCat.png");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(780, 900);
        setResizable(false);
        setTitle("LibCat");
        setIconImage(icon.getImage());
        getContentPane().setBackground(C_ListBG);
        setVisible(true);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));



        for (Order order : ((Customer)user).getCart().getPendingOrders()) {

            JTextPane bookLabel = new JTextPane();
            bookLabel.setFont(new Font("Arial", Font.BOLD, 18));
            bookLabel.setOpaque(false);
            bookLabel.setEditable(false);
            bookLabel.setFocusable(false);

            //Main Panel
            JPanel ListItemPanel = new JPanel();
            ListItemPanel.setLayout(new GridBagLayout());
            ListItemPanel.setBackground(C_ListBG);
            ListItemPanel.setPreferredSize(new Dimension(700, 350));
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

            double discountedPrice = order.getBook().getPrice() * (1 - order.getBook().getSalePercent());
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double salePrice = Double.parseDouble(decimalFormat.format(discountedPrice));

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

                    if(order.getBook().getSalePercent() > 0.0) {

                        order.setQuantity(order.getQuantity() + 1);
                        order.setTotalPrice(order.getTotalPrice() + salePrice);

                        System.out.println(order.getQuantity());
                        System.out.println(((Customer) user).getCart().getPendingOrders());
                    }
                    else{
                        order.setQuantity(order.getQuantity() + 1);
                        order.setTotalPrice(order.getTotalPrice() + order.getBook().getPrice());

                        System.out.println(order.getQuantity());
                        System.out.println(((Customer) user).getCart().getPendingOrders());
                    }

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            salePrice,
                            order.getQuantity()));

                    //containerPanel.revalidate();
                    //containerPanel.repaint();
                }
            });
            decQuantity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(order.getQuantity() > 1){

                        if(order.getBook().getSalePercent() > 0.0) {

                            order.setQuantity(order.getQuantity() - 1);
                            order.setTotalPrice(order.getTotalPrice() - salePrice);

                            System.out.println(order.getQuantity());
                            System.out.println(((Customer) user).getCart().getPendingOrders());
                        }
                        else{
                            order.setQuantity(order.getQuantity() - 1);
                            order.setTotalPrice(order.getTotalPrice() - order.getBook().getPrice());

                            System.out.println(order.getQuantity());
                            System.out.println(((Customer) user).getCart().getPendingOrders());
                        }

                    }
                    else{

                        ((Customer) user).getCart().deletePurchase(order.getBook());
                        System.out.println(((Customer) user).getCart().getPendingOrders());

                    }

                    bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                            order.getBook().getTitle(),
                            salePrice,
                            order.getQuantity()));

                    //containerPanel.revalidate();
                    //containerPanel.repaint();
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
            bookLabel.setText(String.format("Title: %s\n\nPrice: $%s\n\nQuantity: %s",
                order.getBook().getTitle(),
                salePrice,
                order.getQuantity()));

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


            ListItemPanel.add(imageLabel, gbcImageLabel);
            ListItemPanel.add(bookLabel, gbcBookLabel);
            ListItemPanel.add(buttonContainerPanel, gbcButtonContainerPanel);
            ListItemPanel.add(emptyPanel, gbcEmptyPanel);
            containerPanel.add(ListItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
    }
}

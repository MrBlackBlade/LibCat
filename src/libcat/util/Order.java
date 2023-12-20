package libcat.util;

import libcat.Library;
import libcat.StringArrayRepresentation;

import java.util.Collections;
import java.util.ArrayList;

public class Order implements StringArrayRepresentation, Comparable<Order> {
    private int orderID;
    private User user;
    private Book book;

    private double totalPrice;


    private int quantity;

    public Order(int orderID, int customerID, int bookID, int quantity, double price) {
        this.orderID = orderID;
        this.user = (User) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(customerID)).get(0);
        this.book = (Book) Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.ID, String.valueOf(bookID)).get(0);
        this.totalPrice = price;
        this.quantity = quantity;
    }

    public Order(Customer customer, Book book, int quantity) {
        this(
                Collections.max(Library.getOrders()).getID() + 1,
                customer.getID(),
                book.getID(),
                quantity,
                calculateTotalPrice(quantity, book)
        );
    }

    public Order(Customer customer, Book book, int quantity, ArrayList<Order> pendingOrders) {
        this(
                Collections.max(Library.mergeArrays(Library.getOrders(), pendingOrders)).getID() + 1,
                customer.getID(),
                book.getID(),
                quantity,
                calculateTotalPrice(quantity, book)
        );
    }


    public int getID() {
        return orderID;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private static double calculateTotalPrice(double quantity, Book book) {
        return quantity * book.getSalePrice();
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public int compareTo(Order o) {
        return Math.max(this.getID(), o.getID());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", user=" + user +
                ", book=" + book +
                ", totalPrice=" + totalPrice +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public String[] toStringArray() {
        return new String[]{
            String.valueOf(getID()),
            String.valueOf(getUser().getID()),
            String.valueOf(getBook().getID()),
            String.valueOf(getQuantity()),
            String.valueOf(getTotalPrice())
        };
    }
}


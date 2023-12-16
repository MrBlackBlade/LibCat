package libcat.util;

import libcat.Library;

import java.util.Comparator;

public class Order implements Comparable<Order> {
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
                Library.getMax(Library.getOrders()).getID() + 1,
                customer.getID(),
                book.getBookID(),
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
        return quantity * (book.getBookPrice() * (1 - book.getSalePercent()));
    }

    @Override
    public int compareTo(Order o) {
        return Math.max(this.getID(), o.getID());
    }
}


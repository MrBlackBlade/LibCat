package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Customer extends User {
    private Cart cart;
    private ArrayList<Book> reservedPurchases;
    private ArrayList<Book> reservedBorrows;

    public Customer(int id, String name) {
        super(id, name);
        cart = new Cart(this);
        reservedPurchases = new ArrayList<Book>();
        reservedBorrows = new ArrayList<Book>();
    }

    @Override
    public String getType() {
        return "customer";
    }

    public String toString() {
        return String.format("Customer ID: %d, Customer Username: %s", getID(), getName());
    }
    public void setName(String name) {
        super.setName(name);
    }

    public Cart getCart() {
        return cart;
    }


    public ArrayList<Order> getOrderHistory() {
        return Library.getBy(Library.QueryType.ORDER, Library.OrderQueryIndex.USER_ID, String.valueOf(getID()));
    }

    public ArrayList<Transaction> getBorrowHistory() {
        return Library.getBy(Library.QueryType.TRANSACTION, Library.TransactionQueryIndex.USER_ID, String.valueOf(getID()));
    }

    public void rateBook(Book book, boolean like, String review) {
        // check if review is null or empty, sets it to "NO_REVIEW" if any of those
        String finalReview = (review == null || review.isBlank() ? "NO_REVIEW" : review);

        for (Order order : getOrderHistory()) {
            if (order.getBook().equals(book)) {
                Library.getRatings().add(new Rating(
                        book,
                        this,
                        like,
                        finalReview
                ));
            }
        }
    }
}

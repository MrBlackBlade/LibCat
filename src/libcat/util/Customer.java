package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Customer extends User {
    private Cart cart;
    private ArrayList<Book> reservedPurchases;
    private ArrayList<Book> reservedBorrows;
    private String PhoneNumber;
    private String Email;

    public Customer(int id, String name, String PhoneNumber, String Email) {
        super(id, name);
        cart = new Cart(this);
        reservedPurchases = new ArrayList<Book>();
        reservedBorrows = new ArrayList<Book>();
        this.PhoneNumber = PhoneNumber;
        this.Email =  Email;
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
    public void setEmail(String Email) {this.Email = Email;}
    public void setPhoneNumber(String PhoneNumber) {this.PhoneNumber = PhoneNumber;}

    public Cart getCart() {
        return cart;
    }
    public String getPhoneNumber() {return PhoneNumber;}
    public String getEmail() {return Email;}

    public boolean hasBorrows() {
        boolean hasBorrows = false;
        for (Transaction transaction : getBorrowHistory()) {
            hasBorrows = !transaction.isReturned() || hasBorrows;
        }
        return hasBorrows;
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
    @Override
    public String[] toStringArray() {
        return new String[]{
                String.valueOf(getID()),
                getName(),
                getType(),
                getPhoneNumber(),
                getEmail()
        };
    }
}

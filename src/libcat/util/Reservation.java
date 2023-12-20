package libcat.util;

import java.util.ArrayList;

public class Reservation {
    private final Customer customer;
    private ArrayList<Book> reservedPurchases;
    private ArrayList<Book> reservedBorrows;

    public Reservation(Customer customer) {
        this.customer = customer;
        reservedPurchases = new ArrayList<Book>();
        reservedBorrows = new ArrayList<Book>();
    }

    public void addPurchaseReservation(Book book) {
        if (!reservedPurchases.contains(book) && !book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
            reservedPurchases.add(book);
        }
    }
    public void addBorrowReservation(Book book) {
        if (!reservedBorrows.contains(book) && !book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
            reservedBorrows.add(book);
        }
    }

    public boolean removePurchaseReservation(Book book) {
        if (!book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
            // notify the user that the book is available now
            return reservedPurchases.remove(book);
        }

        return false;
    }

    public boolean removeBorrowReservation(Book book) {
        if (!book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
            // notify the user that the book is available now
            return reservedBorrows.remove(book);
        }

        return false;
    }

    public ArrayList<Book> getReservedPurchases() {
        return reservedPurchases;
    }
    public ArrayList<Book> getReservedBorrows() {
        return reservedBorrows;
    }
}

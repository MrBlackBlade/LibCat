package libcat.util;

import libcat.Library;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Collections;
import java.util.ArrayList;

public class Transaction implements Comparable<Transaction> {
    private int transactionID;
    private User user;
    private Book book;
    private double fine;
    private LocalDate borrowDate;
    private boolean isReturned;

    public Transaction(int transactionID, int borrowerID, int bookID, String borrowDate, boolean isReturned) {
        this.transactionID = transactionID;
        this.user = (User) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(borrowerID)).get(0);
        this.book = (Book) Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.ID, String.valueOf(bookID)).get(0);

        String[] borrowDateArray = borrowDate.split("-");

        this.borrowDate = LocalDate.of(Integer.parseInt(borrowDateArray[0]), Integer.parseInt(borrowDateArray[1]), Integer.parseInt(borrowDateArray[2]));
        this.isReturned = isReturned;
        this.applyFine();
    }

    public Transaction(Customer customer, Book book) {
        this(
                Collections.max(Library.getTransactions()).getID() + 1,
                customer.getID(),
                book.getID(),
                LocalDate.now().toString(),
                false
        );
    }

    public Transaction(Customer customer, Book book, ArrayList<Transaction> pendingTransactions) {
        this(
                Collections.max(Library.mergeArrays(Library.getTransactions(), pendingTransactions)).getID() + 1,
                customer.getID(),
                book.getID(),
                LocalDate.now().toString(),
                false
        );
    }

    public boolean overDue() {
        return LocalDate.now().isAfter(getReturnDate());
    }

    // automatically called when the user logs in to check if there's a fine or not
    public void applyFine() {
        fine = book.getPrice() * (!isReturned && overDue() ? 0.15 : 0.0);
    }

    public int getID() {
        return transactionID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return borrowDate.plusWeeks(3);
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public double getFine() {
        return fine;
    }

    public boolean isReturned() {
        return isReturned;
    }

    @Override
    public int compareTo(Transaction o) {
        return Math.max(this.getID(), o.getID());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", user=" + user +
                ", book=" + book +
                ", fine=" + fine +
                ", borrowDate=" + borrowDate +
                ", isReturned=" + isReturned +
                '}';
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
        applyFine();
    }
}

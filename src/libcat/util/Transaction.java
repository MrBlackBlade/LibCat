package libcat.util;

import libcat.Library;

import java.time.LocalDate;
import java.util.Collections;

public class Transaction implements Comparable<Transaction> {
    private int transactionID;
    private User user;
    private Book book;
    private double totalPrice;
    private double fine;
    private LocalDate borrowDate;
    private boolean isReturned;

    public Transaction(int transactionID, int borrowerID, int bookID, String borrowDate) {
        this.transactionID = transactionID;
        this.user = (User) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(borrowerID)).get(0);
        this.book = (Book) Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.ID, String.valueOf(bookID)).get(0);

        String[] borrowDateArray = borrowDate.split("-");

        this.borrowDate = LocalDate.of(Integer.parseInt(borrowDateArray[0]), Integer.parseInt(borrowDateArray[1]), Integer.parseInt(borrowDateArray[2]));

        this.applyFine();
    }

    public Transaction(Borrower borrower, Book book) {
        this(
                Collections.max(Library.getTransactions()).getID() + 1,
                borrower.getID(),
                book.getID(),
                LocalDate.now().toString()
        );
    }

    private boolean overDue() {
        return !LocalDate.now().isAfter(this.borrowDate.plusWeeks(3));
    }

    // automatically called when the user logs in to check if there's a fine or not
    public boolean checkFine() {
        boolean hasFine;

        if (this.overDue()) {
            this.fine = 0.0;
            hasFine = false;
        } else {
            this.fine = 0.15;
            hasFine = true;
        }

        return hasFine;
    }

    public void applyFine() {
         this.totalPrice = this.book.getPrice() * this.getFine();
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

    @Override
    public int compareTo(Transaction o) {
        return Math.max(this.getID(), o.getID());
    }
}

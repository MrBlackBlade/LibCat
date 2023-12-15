package libcat.util;

import libcat.Library;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction> {
    private int transactionID;
    private Borrower borrower;
    private Book book;
    private double totalPrice;
    private double fine;
    private LocalDate borrowDate;
    private boolean isReturned;

    public Transaction(int transactionID, int borrowerID, int bookID, String borrowDate) {
        this.transactionID = transactionID;
        this.borrower = (Borrower) Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(borrowerID)).get(0);
        this.book = (Book) Library.getBy(Library.QueryType.BOOK, Library.BookQueryIndex.ID, String.valueOf(bookID)).get(0);

        String[] borrowDateArray = borrowDate.split("-");

        this.borrowDate = LocalDate.of(Integer.parseInt(borrowDateArray[0]), Integer.parseInt(borrowDateArray[1]), Integer.parseInt(borrowDateArray[2]));

        this.applyFine();
    }

    public Transaction(Borrower borrower, Book book) {
        this(
            Library.getMax(Library.getTransactions()).getID() + 1,
                borrower.getID(),
                book.getBookID(),
                //
                LocalDate.now().toString()
                //
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
        /*check local date with rdate -> is it passed or not
        passed => fine
        there should be value to return the fine to in the borrower class
        original price for the book;
        return the fine to the borrower class in :
        fine variable then added to the price
        */
         this.totalPrice = this.book.getBookPrice() * this.getFine();
    }

    public int getID() {
        return transactionID;
    }
    public double getFine() {
        return fine;
    }
    @Override
    public int compareTo(Transaction o) {
        return Math.max(this.getID(), o.getID());
    }
}

package libcat;

import libcat.util.*;


import java.util.Iterator;
import libcat.util.Customer;
import libcat.util.User;

public class Admin extends User{
    public Admin(int id, String name) {
        super(id, name);
    }
    @Override
    public String getType() {
        return "admin";
    }
    public String toString() {
        return String.format("Admin ID: %d, Admin Username: %s", getID(), getName());
    }

    protected static void addCustomer(int id, String name) {
        Library.customers.add(new Customer(id, name));
    }

    protected static void addBook(Book newBook) {
        Library.books.add(newBook);
    }

    protected static void updateBook(
            int bookID,
            String newTitle,
            String newAuthor,
            String newGenre,
            String newYear,
            double newPrice,
            double newSalePercent,
            boolean newAvailability
    ) {
        for (Book book : Library.books) {
            if (book.getBookID() == bookID) {
                book.setBookTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setGenre(newGenre);
                book.setYear(newYear);
                book.setBookPrice(newPrice);
                book.setSalePercent(newSalePercent);
                book.setAvailable(newAvailability);
                break;
            }
        }
    }

    protected static void deleteBook(int bookID) {
        Iterator<Book> iterator = Library.books.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getBookID() == bookID) {
                iterator.remove();
                break;
            }
        }
    }

    protected static void addBorrower(
            int ID,
            String name
    ) {
        Borrower newBorrower = new Borrower(ID, name);
        Library.borrowers.add(newBorrower);
    }

    // Overload: Adds a Borrower to the borrowers list using ID, and removes the corresponding Customer with the same ID
    protected static void addBorrower(Customer customer) {

        Library.borrowers.add(new Borrower(customer.getID(), customer.getName()));

        Iterator<Customer> iterator = Library.customers.iterator();
        while (iterator.hasNext()) {
            Customer customerIterator = iterator.next();
            if (customerIterator.getID() == customer.getID()) {
                iterator.remove();
                break;
            }
        }
    }


    protected static void updateBorrower(
            int borrowerID,
            String borrowerName
    ) {
        for (Borrower borrower : Library.borrowers) {
            if (borrower.getID() == borrowerID) {
                borrower.setName(borrowerName);
                break;
            }
        }
    }

    protected static void deleteBorrower(int borrowerID) {
        Iterator<Borrower> iterator = Library.borrowers.iterator();

        while (iterator.hasNext()) {
            Borrower borrower = iterator.next();
            if (borrower.getID() == borrowerID) {
                iterator.remove();
                break;
            }
        }
    }
}
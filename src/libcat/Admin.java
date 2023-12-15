package libcat;

import libcat.util.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Admin extends User implements UserType {
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

    public static void addBook(Book newBook) {
        Library.books.add(newBook);
    }

    public static void updateBook(
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

    public static void deleteBook(int bookID) {
            Iterator<Book> iterator = Library.books.iterator();

            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getBookID() == bookID) {
                    iterator.remove();
                    break;
                }
            }
        }

}


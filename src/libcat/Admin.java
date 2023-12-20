package libcat;

import libcat.util.*;


import libcat.util.Customer;
import libcat.util.User;

public class Admin extends User{
    public Admin(int id, String name) {
        super(id, name);
    }

    protected static void addCustomer(int id, String name) {
        Library.getCustomers().add(new Customer(id, name));
    }

    protected static void addBook(Book newBook) {
        Library.getBooks().add(newBook);
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
        for (Book book : Library.getBooks()) {
            if (book.getID() == bookID) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setGenre(newGenre);
                book.setYear(newYear);
                book.setBasePrice(newPrice);
                book.setSalePercent(newSalePercent);
                book.setAvailable(newAvailability);
                break;
            }
        }
    }

    protected static boolean deleteBook(Book book) {
        boolean deleteSuccessful = false;
        if (Library.getBooks().contains(book)) {
            Library.getBooks().remove(book);
            deleteSuccessful = true;
        }
        return deleteSuccessful;
    }

    protected static void addBorrower(
            int ID,
            String name
    ) {
        Borrower newBorrower = new Borrower(ID, name);
        Library.getBorrowers().add(newBorrower);
    }

    // Overload: Adds a Borrower to the borrowers list using ID, and removes the corresponding Customer with the same ID
    protected static Borrower convert(Customer customer) {
        Borrower newBorrower = new Borrower(customer.getID(), customer.getName());

        Library.getBorrowers().add(newBorrower);
        Library.getCustomers().remove(customer);

        return newBorrower;
    }

    protected static Customer convert(Borrower borrower) {
        Customer newCustomer = new Customer(borrower.getID(), borrower.getName());

        Library.getCustomers().add(newCustomer);
        Library.getBorrowers().remove(borrower);

        return newCustomer;
    }


    protected static void updateUser(
            int userID,
            String userName
    ) {
        for (Borrower borrower : Library.getBorrowers()) {
            if (borrower.getID() == userID) {
                borrower.setName(userName);
                break;
            }
        }
        for (Customer customer : Library.getCustomers()){
            if(customer.getID() == userID){
                customer.setName(userName);
            }
        }
    }

    protected static boolean deleteUser(User user) {
        boolean deleteSuccessful = false;
        if (Library.getBorrowers().contains(user)) {
            Library.getBorrowers().remove(user);
            deleteSuccessful = true;
        }
        if (Library.getCustomers().contains(user)){
            Library.getCustomers().remove(user);
            deleteSuccessful = true;
        }
        return deleteSuccessful;
    }
    @Override
    public String getType() {
        return "admin";
    }
    public String toString() {
        return String.format("Admin ID: %d, Admin Username: %s", getID(), getName());
    }
}

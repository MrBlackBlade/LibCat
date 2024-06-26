package libcat;

import libcat.util.*;

import libcat.util.Customer;
import libcat.util.User;

public class Admin
		extends User {
	public Admin(int id, String name, String password, String phoneNumberString, String email) {
		super(id, name, password, phoneNumberString, email);
	}

	protected static void addCustomer(int id, String name, String password, String phoneNumber, String email) {
		Library.getCustomers().add(new Customer(id, name, password, phoneNumber, email));
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
			double newSalePercent
	) {
		for (Book book : Library.getBooks()) {
			if (book.getID() == bookID) {
				book.setTitle(newTitle);
				book.setAuthor(newAuthor);
				book.setGenre(newGenre);
				book.setYear(newYear);
				book.setBasePrice(newPrice);
				book.setSalePercent(newSalePercent);
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
			String name,
			String password,
			String phoneNumber,
			String email
	) {
		Borrower newBorrower = new Borrower(ID, name, password, phoneNumber, email);
		Library.getBorrowers().add(newBorrower);
	}

	// Overload: Adds a Borrower to the borrowers list using ID, and removes the corresponding Customer with the same ID
	protected static Borrower convertToBorrower(Customer customer) {
		Borrower newBorrower = new Borrower(customer.getID(), customer.getName(), customer.getPassword(), customer.getPhoneNumber(), customer.getEmail());
		boolean foundInLibrary = false;
		for (Borrower borrower : Library.getBorrowers()) {
			if ((customer.getID() == borrower.getID())) {
				foundInLibrary = true;
			}
		}
		if (!foundInLibrary) {
			Library.getBorrowers().add(newBorrower);
			Library.getCustomers().remove(customer);
		}

		return newBorrower;
	}

	protected static Customer convertToCustomer(Customer borrower) {
		Customer newCustomer = new Customer(borrower.getID(), borrower.getName(), borrower.getPassword(), borrower.getPhoneNumber(), borrower.getEmail());
		boolean foundInLibrary = false;
		for (Customer customer : Library.getCustomers()) {
			if ((borrower.getID() == customer.getID())) {
				foundInLibrary = true;
			}
		}
		if (!foundInLibrary) {
			Library.getCustomers().add(newCustomer);
			Library.getBorrowers().remove(borrower);
		}

		return newCustomer;
	}

	protected static Customer convertToCustomer(Borrower borrower) {
		Customer newCustomer = new Customer(borrower.getID(), borrower.getName(), borrower.getPassword(), borrower.getPhoneNumber(), borrower.getEmail());
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
		for (Customer customer : Library.getCustomers()) {
			if (customer.getID() == userID) {
				customer.setName(userName);
			}
		}
	}

	protected static boolean deleteBorrower(Borrower borrower) {
		boolean deleteSuccessful = false;
		for (Transaction transaction : borrower.getBorrowHistory()) {
			transaction.setReturned(true);
		}
		if (!borrower.hasBorrows()) {
			Admin.convertToCustomer(borrower);
		}
		deleteSuccessful = true;
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

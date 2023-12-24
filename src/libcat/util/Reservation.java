package libcat.util;

import libcat.Library;
import libcat.StringArrayRepresentation;

public class Reservation
		implements StringArrayRepresentation {
	private final Customer customer;
	private String reservationType;
	private Book reservedBook;

	public Reservation(String reservationType, int userID, int bookID) {
		this.reservationType = reservationType;

		this.customer = (Customer) Library.getBy(
				Library.QueryType.USER,
				Library.UserQueryIndex.ID,
				String.valueOf(userID)
		).get(0);

		this.reservedBook = (Book) Library.getBy(
				Library.QueryType.BOOK,
				Library.BookQueryIndex.ID,
				String.valueOf(bookID)
		).get(0);
	}

	public Reservation(String reservationType, Customer customer, Book book) {
		this(
				reservationType,
				customer.getID(),
				book.getID()
		);
	}

	public Customer getCustomer() {
		return customer;
	}

	public Book getBook() {
		return reservedBook;
	}

	public String getType() {
		return reservationType;
	}

	@Override
	public String[] toStringArray() {
		return new String[] {
				String.valueOf(customer.getID()),
				String.valueOf(reservedBook.getID())
		};
	}
}

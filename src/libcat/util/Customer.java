package libcat.util;

import libcat.Library;

import javax.swing.*;
import java.util.ArrayList;

public class Customer
		extends User {
	private Cart cart;

	public Customer(int id, String name, String password, String phoneNumber, String email) {
		super(id, name, password, phoneNumber, email);
		cart = new Cart(this);
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

	public void setEmail(String email) {
		super.setEmail(email);
	}

	public void setPhoneNumber(String phoneNumber) {
		super.setPhoneNumber(phoneNumber);
	}

	public Cart getCart() {
		return cart;
	}

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
		String finalReview = (review == null || review.isBlank() ? "" : review);

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
		for (Transaction transaction : getBorrowHistory()) {
			if (transaction.getBook().equals(book)) {
				Library.getRatings().add(new Rating(
						book,
						this,
						like,
						finalReview
				));
			}

		}
	}

	public boolean seenBook(Book book) {
		boolean seen = false;
		for (Order order : getOrderHistory()) {

			seen = order.getBook().equals(book) ? true : seen;

		}
		for (Transaction transaction : getBorrowHistory()) {

			seen = transaction.getBook().equals(book) ? true : seen;

		}
		return seen;
	}

	public StringBuilder getNotifications() {
		StringBuilder notifications = new StringBuilder();
		for (Reservation reservation : Library.getUserBorrowReservations(this)) {
			if (reservation.getBook().getPurchaseStatus().get(Book.Availablity.BORROWABLE)) {
				Library.removeBorrowReservation(reservation.getBook(), this);
				notifications.append(String.format("%s is now available for borrowing!\n", reservation.getBook().getTitle()));
			}
		}
		for (Reservation reservation : Library.getUserPurchaseReservations(this)) {
			if (reservation.getBook().getPurchaseStatus().get(Book.Availablity.PURCHASABLE)) {
				Library.removePurchaseReservation(reservation.getBook(), this);
				notifications.append(String.format("%s is now available for purchase!\n", reservation.getBook().getTitle()));
			}
		}
		for (Transaction transaction : getBorrowHistory()) {
			if (transaction.overDue() && !transaction.isReturned()) {
				notifications.append(String.format("Transaction #%d is return is overdue!\n", transaction.getID()));
			}
		}
		return notifications;
	}

	@Override
	public String[] toStringArray() {
		return new String[] {
				String.valueOf(getID()),
				getName(),
				getPassword(),
				getPhoneNumber(),
				getEmail(),
				getType()
		};
	}
}

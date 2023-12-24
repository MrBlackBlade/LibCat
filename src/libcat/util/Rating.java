package libcat.util;

import libcat.Library;
import libcat.StringArrayRepresentation;

import java.util.ArrayList;

public class Rating
		implements StringArrayRepresentation {
	private Book book;
	private Customer customer;
	private boolean like;
	private String review;

	public Rating(Book book, Customer customer, boolean like, String review) {
		this.book = book;
		this.customer = customer;
		this.like = like;
		this.review = review;
	}

	public int getBookID() {
		return book.getID();
	}

	public boolean isLike() {
		return like;
	}

	public String getReview() {
		return review;
	}

	public Book getBook() {
		return book;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getUsername() {
		return customer.getName();
	}

	@Override
	public String toString() {
		return "Rating{" +
				"book=" + book +
				", customer=" + customer +
				", like=" + like +
				", review='" + review + '\'' +
				'}';
	}

	@Override
	public String[] toStringArray() {
		return new String[] {
				String.valueOf(getBook().getID()),
				String.valueOf(getCustomer().getID()),
				String.valueOf(isLike()),
				String.valueOf(getReview())
		};
	}
}

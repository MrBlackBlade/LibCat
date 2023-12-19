package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Rating {
    private Book book;
    private Customer customer;
    private boolean like;
    private String review;

    public Rating(Book book, Customer customer, boolean like, String review){
        this.book = book;
        this.customer = customer;
        this.like = like;
        this.review = review;
    }

    public int getBookID() { return book.getID(); }
    public boolean isLike() { return like; }
    public String getReview() {return review;}

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
}

package libcat.util;

import libcat.Library;

import java.util.ArrayList;

public class Rating {
    private int bookID;
    private int customerID;
    private boolean like;
    private String review;

    public Rating(int bookID, int customerID, boolean like, String review){
        this.bookID = bookID;
        this.customerID = customerID;
        this.like = like;
        this.review = review;
    }

    public int getBookID() { return bookID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public boolean isLike() { return like; }
    public String getReview() {return review;}

    public String getUsername() {
        ArrayList<User> queryResult = new ArrayList<User>();
        queryResult = Library.getBy(Library.QueryType.USER, Library.UserQueryIndex.ID, String.valueOf(this.customerID));
        return queryResult.get(0).getName();
    }
    @Override
    public String toString() {
        return "Rating{" +
                "bookID=" + bookID +
                ", customerID=" + customerID +
                ", like=" + like +
                ", review='" + review + '\'' +
                '}';
    }
}

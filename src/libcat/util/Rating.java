package libcat.util;

import java.util.ArrayList;

public class Rating {
    int bookID;
    int customerID;
    boolean like;



    String review;

    public Rating(int bookID, int customerID, boolean like, String review){
        this.bookID = bookID;
        this.customerID = customerID;
        this.like = like;
        this.review = review;
    }

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

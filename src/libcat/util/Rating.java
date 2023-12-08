package libcat.util;

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

package libcat.util;

import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Float.isNaN;

public class Book {
    private int bookID;
    private String bookTitle;
    private String author;
    private String genre;
    private boolean available; //status
    private String year;
    private double bookPrice;
    private double salePercent;
    private float rating;

    private ImageIcon imageIcon;

    private ArrayList<Rating> ratings;

    public Book(
            int bookID,
            String bookTitle,
            String author,
            String genre,
            String year,
            ArrayList<Rating> ratings,
            double bookPrice,
            double salePercent,
            boolean available,
            ImageIcon imageIcon
    ) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.ratings = ratings;
        this.bookPrice = bookPrice;
        this.salePercent = salePercent;
        this.available = available;
        this.imageIcon = imageIcon;

        this.rating = calculateRating(this.ratings);
    }

    public void setBookID(int a) {
        this.bookID = a;
    }

    public void setBookTitle(String b) {
        this.bookTitle = b;
    }

    public void setAuthor(String c) {
        this.author = c;
    }

    public void setGenre(String d) {
        this.genre = d;
    }

    public void setStatus(boolean e) {
        this.available = e;
    }

    public void setBookPrice(double f) {
        this.bookPrice = f;
    }

    public void setSalePercent(double g) {
        this.salePercent = g;
    }

    public int getBookID() {
        return this.bookID;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public boolean getStatus() {
        return this.available;
    }

    public double getBookPrice() {
        return this.bookPrice;
    }

    public double getSalePercent() {
        return this.salePercent;
    }

    public float getRating() {
        return rating;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", available=" + available +
                ", year='" + year + '\'' +
                ", bookPrice=" + bookPrice +
                ", salePercent=" + salePercent +
                ", rating=" + rating +
                ", imageIcon=" + imageIcon +
                ", ratings=" + ratings +
                '}';
    }

    private float calculateRating(ArrayList<Rating> ratings) {
        float sum = 0;
        for (Rating rating : ratings) {
            sum = rating.isLike() ? sum + 1 : sum;
        }
        return isNaN((sum / ratings.size())) ? -1F : (sum / ratings.size()) * 100F;
    }
}

package libcat.util;

import java.util.Arrays;

public class Book {

    private int bookID;
    private String bookTitle;
    private String author;
    private String genre;
    private boolean available; //status
    private String year;
    private double bookPrice;
    private double salePercent;
    private float[] rating;
    private float finalRating;
    private String[] reviews;
    private boolean returned;

    public Book(
            int bookID,
            String bookTitle,
            String author,
            String genre,
            String year,
            float[] rating,
            double bookPrice,
            double salePercent,
            boolean available
    )
    {
        this.bookID         = bookID;
        this.bookTitle      = bookTitle;
        this.author         = author;
        this.genre          = genre;
        this.year           = year;
        this.rating         = rating;
        this.bookPrice      = bookPrice;
        this.salePercent    = salePercent;
        this.available      = available;
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

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturned() {
        return this.returned;
    }

    public void setBookPrice(double f) {
        this.bookPrice = f;
    }

    public void setSalePercent(double g) {
        this.salePercent = g;
    }

    public void setRating(float[] i) {
        this.rating = i;
    }

    public void setReviews(String[] h) {
        this.reviews = h;
    }

    public void setFinalrating() {
        this.finalRating = calculateRating(this.rating);
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

    public float[] getRating() {
        return this.rating;
    }

    public String[] getReviews() {
        return this.reviews;
    }

    public float calculateRating(float[] r) {
        float sum = 0;

        for (int i = 0; i < r.length; i++) {
            sum += r[i];
        }

        float Frating = sum / r.length;
        return Frating;
    }

}

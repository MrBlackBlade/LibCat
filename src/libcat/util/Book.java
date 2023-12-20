package libcat.util;

import libcat.FileSystemManager;
import libcat.Library;
import libcat.StringArrayRepresentation;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Float.isNaN;

public class Book implements StringArrayRepresentation, Comparable<Book>{

    public enum Availablity {
        PURCHASABLE, BORROWABLE, AVAILABLITY_MAX,
    }
    private int id;
    private String title;
    private String author;
    private String genre;
    private HashMap<Availablity, Boolean> status = new HashMap<Availablity, Boolean>();
    private String year;
    private double basePrice;
    private double salePercent;
    private float rating;

    private final String imagePath;
    private final ImageIcon imageIcon;

    private ArrayList<Rating> ratings;

    public Book(
            int id,
            String title,
            String author,
            String genre,
            String year,
            double basePrice,
            double salePercent,
            boolean purchasable,
            boolean borrowable,
            String imagePath
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.basePrice = basePrice;
        this.salePercent = salePercent;
        status.put(Availablity.PURCHASABLE, purchasable);
        status.put(Availablity.BORROWABLE, borrowable);
        this.imagePath = imagePath;
        this.imageIcon = new ImageIcon(FileSystemManager.getImageWD() + imagePath);
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String d) {
        this.genre = d;
    }



    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setSalePercent(double salePercent) {
        this.salePercent = salePercent;
    }

    public HashMap<Availablity, Boolean> getPurchaseStatus() {
        return status;
    }

    public int getID() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getYear() {
        return year;
    }

    public double getBasePrice() {
        return this.basePrice;
    }

    public double getSalePrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(this.basePrice * (1-salePercent)));
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

    public String getImagePath() {
        return imagePath;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    private float calculateRating() {
        float sum = 0;
        for (Rating rating : ratings) {
            sum = rating.isLike() ? sum + 1 : sum;
        }
        return isNaN((sum / ratings.size())) ? -1F : (sum / ratings.size()) * 100F;
    }

    @Override
    public int compareTo(Book o) {
        return Math.max(this.getID(), o.getID());
    }

    public void setYear(String newYear) {
    }

    public void setAvailable(boolean newAvailability) {
    }
    public void initializeRatings() {
        this.ratings = Library.getBy(Library.QueryType.RATING, Library.RatingQueryIndex.BOOK_ID, String.valueOf(this.getID()));

        this.rating = calculateRating();
    }

    @Override
    public String[] toStringArray() {
        return new String[]{
                String.valueOf(getID()),
                String.valueOf(getTitle()),
                String.valueOf(getAuthor()),
                String.valueOf(getGenre()),
                String.valueOf(getYear()),
                String.valueOf(getRating()),
                String.valueOf(getBasePrice()),
                String.valueOf(getSalePercent()),
                String.valueOf(getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
                String.valueOf(getPurchaseStatus().get(Book.Availablity.BORROWABLE)),
                String.valueOf(getImagePath())
        };
    }
}

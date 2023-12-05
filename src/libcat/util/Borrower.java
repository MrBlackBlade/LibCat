package libcat.util;

public class Borrower extends Customer{
    public Borrower(int id, String name) {
        super(id, name);
    }
    public String toString() {
        return String.format("Borrower ID: %d, Borrower Username: %s", getID(), getName());
    }
}

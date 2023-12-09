package libcat.util;

public class Borrower extends Customer implements UserType {
    public Borrower(int id, String name) {
        super(id, name);
    }
    @Override
    public String getType() {
        return "borrower";
    }
    public String toString() {
        return String.format("Borrower ID: %d, Borrower Username: %s", getID(), getName());
    }
}

package libcat.util;

public class Borrower extends Customer{
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

    public void setName(String name) {
        super.setName(name);
    }

    public void deductFine(double fine) {
    }

    public CharSequence getBooksBorrowed() {
    }
}

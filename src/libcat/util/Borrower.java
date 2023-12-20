package libcat.util;

public class Borrower extends Customer {
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

    public void returnBook(Transaction transaction) {
        transaction.setReturned(true);
    }


    public double getTotalFines() {
        double totalFines = 0;
        for (Transaction transaction : getBorrowHistory()) {
            totalFines += transaction.getFine();
        }
        return totalFines;
    }
}

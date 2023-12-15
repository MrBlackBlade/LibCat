package libcat.util;

public class Customer extends User {
    public Customer(int id, String name) {
        super(id, name);
    }
    @Override
    public String getType() {
        return "customer";
    }
    public String toString() {
        return String.format("Customer ID: %d, Customer Username: %s", getID(), getName());
    }
}

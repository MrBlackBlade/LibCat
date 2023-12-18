package libcat;

import libcat.util.Customer;
import libcat.util.User;

public class Admin extends User {
    public Admin(int id, String name) {
        super(id, name);
    }
    @Override
    public String getType() {
        return "admin";
    }
    public String toString() {
        return String.format("Admin ID: %d, Admin Username: %s", getID(), getName());
    }

    protected static void addCustomer(int id, String name) {
        Library.customers.add(new Customer(id, name));
        FileSystemManager.updateData(FileSystemManager.usersDataFile);
    }
}

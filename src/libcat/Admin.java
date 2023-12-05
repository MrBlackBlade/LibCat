package libcat;

import libcat.util.User;

public class Admin extends User {
    public Admin(int id, String name) {
        super(id, name);
    }
    public String toString() {
        return String.format("Admin ID: %d, Admin Username: %s", getID(), getName());
    }
}

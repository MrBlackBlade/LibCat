package libcat;

import libcat.util.User;
import libcat.util.UserType;

public class Admin extends User implements UserType {
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
}

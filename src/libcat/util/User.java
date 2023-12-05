package libcat.util;

import java.util.Arrays;
import java.util.Optional;

public abstract class User {
    int id;
    String name;
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return String.format("User ID: %d, User Username: %s", id, name);
    }
}

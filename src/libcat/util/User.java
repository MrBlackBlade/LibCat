package libcat.util;

import java.util.Comparator;

public abstract class User implements Comparable<User> {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getType() {
        return "user";
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

    @Override
    public int compareTo(User o) {
        return Math.max(this.getID(), o.getID());
    }
}

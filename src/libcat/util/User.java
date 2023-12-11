package libcat.util;

public abstract class User implements UserType {
    private int id;
    private String name;
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
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
}

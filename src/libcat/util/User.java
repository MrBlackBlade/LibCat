package libcat.util;

import libcat.StringArrayRepresentation;

import java.util.Comparator;

public abstract class User implements StringArrayRepresentation, Comparable<User>, Comparator<User> {
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User o) {
        //System.out.printf("this: %d, next: %d, winner: %d\n", this.getID(), o.getID(), Math.max(this.getID(), o.getID()));
        return Integer.compare(this.getID(), o.getID());
    }
    @Override
    public int compare(User o1, User o2) {
        return Integer.compare(o1.getID(),o2.getID());
    }
    @Override
    public String[] toStringArray() {
        return new String[]{
                String.valueOf(getID()),
                getName(),
                getType()
        };
    }
}

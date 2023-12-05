package libcat.util;

import java.util.ArrayList;

public class Customer extends User {
    //public ArrayList<String[]> books;
    public Customer(int id, String name) {
        super(id, name);
        //books = FileSystemManager.querey("books.txt");
    }
    public String toString() {
        return String.format("Customer ID: %d, Customer Username: %s", getID(), getName());
    }
}

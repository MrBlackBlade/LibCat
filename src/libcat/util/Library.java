package libcat.util;

import java.util.ArrayList;

public class Library {
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;
    //public static ArrayList<Customer> customers;
    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersFile);
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();
    }
}
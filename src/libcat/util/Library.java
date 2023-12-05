package libcat.util;

import libcat.Admin;

import java.util.ArrayList;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;
    //public static ArrayList<Customer> customers;
    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersFile);
        admins = new ArrayList<Admin>();
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();
        makeUsers();
    }
    private static void makeUsers() {
        ArrayList<String[]> userList = FileSystemManager.querey("usersdata.txt");
        for (String[] userRow:userList) {
            switch (userRow[2]) {
                case "borrower":
                    borrowers.add(new Borrower(Integer.parseInt(userRow[0]), userRow[1]));
                    break;
                case "customer":
                    customers.add(new Customer(Integer.parseInt(userRow[0]), userRow[1]));
                    break;
                case "admin":
                    admins.add(new Admin(Integer.parseInt(userRow[0]), userRow[1]));
            }
        }
        for (Customer customer:customers) {
            System.out.println(customer);
        }
        for (Borrower borrower:borrowers) {
            System.out.println(borrower);
        }
        for (Admin admin:admins) {
            System.out.println(admin);
        }
    }
}

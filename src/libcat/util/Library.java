package libcat.util;

import libcat.Admin;

import java.util.ArrayList;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Book> books;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;

//    public enum QueryType
//    {
//        BOOK_TITLE,
//        BOOK_AUTHOR,
//
//        QUERY_TYPE_MAX,
//    }

    //public static ArrayList<Customer> customers;
    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersCredsFile);

        admins = new ArrayList<Admin>();
        books = new ArrayList<Book>();
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();

        Library.makeUsers();
        Library.makeBooks();
    }

    private static void makeUsers() {
        ArrayList<String[]> userList = FileSystemManager.query("usersdata.txt");

        for (String[] userRow : userList) {
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

        for (Customer customer : customers) {
            System.out.println(customer);
        }

        for (Borrower borrower : borrowers) {
            System.out.println(borrower);
        }

        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    private static void makeBooks() {
        ArrayList<String[]> booksList = FileSystemManager.query(FileSystemManager.booksFile);

        for (String[] row : booksList) {
            books.add(new Book(
                    Integer.parseInt(row[0]),
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    new float[]{Float.parseFloat(row[5])},
                    Double.parseDouble(row[6]),
                    Double.parseDouble(row[7]),
                    Boolean.parseBoolean(row[8])
            ));
        }
    }
}

//    public static void getBy(QueryType query){
//        switch (query)
//        {
//            case BOOK_ID:
//                break;
//            case BOOK_AUTHOR:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//            case ID:
//                break;
//
//
//        }
//    }

//    public static int updateFiles() {
//        // number returned at the end of the function
//        // 0 meaning the files are up-to-date with the arrays
//        // -1 meaning the files aren't up-to-date with the arrays
//        int code = 0;
//
//        int totalUsersNumber = Library.customers.size() + Library.borrowers.size() + Library.admins.size();
//
//        ArrayList<String[]> fileData = FileSystemManager.query(FileSystemManager.usersDataFile);
//
//        for (int i = 0; i < Math.max(fileData.size(), totalUsersNumber); i++) {
//            System.out.println(fileData.get(i)[2]);
//
//        }
//
//        System.out.println(totalUsersNumber == fileData.size());
//
//        if (fileData.size() != (totalUsersNumber))
//            return -1;
//
//        return 0;
//    }
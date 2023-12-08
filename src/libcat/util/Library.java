package libcat.util;

import libcat.Admin;

import java.util.ArrayList;

public class Library {
    public static ArrayList<Book> books;
    public static ArrayList<Rating> ratings;
    public static ArrayList<Admin> admins;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;

    public enum QueryType
    {
        //BOOKID_BY_TITLE,
        //BOOKID_BY_AUTHOR,
        RATING_BY_BOOKID,
        QUERY_TYPE_MAX,
    }

    //public static ArrayList<Customer> customers;
    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersCredsFile);

        books = new ArrayList<Book>();
        ratings = new ArrayList<Rating>();
        admins = new ArrayList<Admin>();
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();

        Library.makeRatings(); //has to be called before books (prolly before users too)
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
                    getRatingsByBookID(Integer.parseInt(row[0])),
                    Double.parseDouble(row[6]),
                    Double.parseDouble(row[7]),
                    Boolean.parseBoolean(row[8])
            ));
        }
    }
    private static void makeRatings() {
        ArrayList<String[]> ratingList = FileSystemManager.query(FileSystemManager.ratingsFile);

        for (String[] row : ratingList) {
            String review = "";
            for (int i = 3; i < row.length; i++) {
                review = review.concat(row[i]);
            }
            ratings.add(new Rating(
                    Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]),
                    Boolean.parseBoolean(row[2]),
                    review
            ));
        }
    }
    private static ArrayList<Rating> getRatingsByBookID(int bookID){
        ArrayList<Rating> queryResult = new ArrayList<Rating>();
        for (Rating rating : ratings) {
            if (rating.bookID == bookID) {
                queryResult.add(rating);
            }
        }
        return queryResult;
    }
//    public static ArrayList<Object> getBy(QueryType query, Object index){
//        ArrayList<Object> queryResult = new ArrayList<Object>();
//        switch (query)
//        {
//            case RATING_BY_BOOKID:
//                int bookID = (Integer) index;
//                for (Rating rating : ratings) {
//                    if (rating.bookID == bookID) {
//                        queryResult.add(rating);
//                    }
//                }
//                break;
//        }
//        return queryResult;
//    }
}
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
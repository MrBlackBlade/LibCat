package libcat.util;

import libcat.Admin;

import javax.swing.*;
import javax.xml.crypto.AlgorithmMethod;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import libcat.util.QueryIndex;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Book> books;
    public static ArrayList<Rating> ratings;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;

    public static ArrayList<User> users;

    public enum QueryType {
        BOOK, USER, RATING,

        QUERY_TYPE_MAX,
    }

    public enum BookQueryIndex implements QueryIndex {
        ID {
            @Override
            public String getQuery() {
                return "book_id";
            }
        }, TITLE {
            @Override
            public String getQuery() {
                return "book_title";
            }
        }, AUTHOR {
            @Override
            public String getQuery() {
                return "book_author";
            }
        }, GENRE {
            @Override
            public String getQuery() {
                return "book_genre";
            }
        }
    }

    public enum UserQueryIndex implements QueryIndex {
        ID {
            @Override
            public String getQuery() {
                return "user_id";
            }
        }, NAME {
            @Override
            public String getQuery() {
                return "user_name";
            }
        }, TYPE {
            @Override
            public String getQuery() {
                return "user_type";
            }
        }
    }

    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersCredsFile);

        books = new ArrayList<Book>();
        ratings = new ArrayList<Rating>();
        admins = new ArrayList<Admin>();
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();
        users = new ArrayList<User>();

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

        users = FileSystemManager.mergeUsers(Library.admins, Library.customers, Library.borrowers);

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
                    getRatingsByBookID(Integer.parseInt(row[0])),
                    Double.parseDouble(row[6]),
                    Double.parseDouble(row[7]),
                    Boolean.parseBoolean(row[8]),
                    new ImageIcon(FileSystemManager.cwd + row[9])
            ));
        }
    }

    public static ArrayList<String> getBy(QueryType queryType, QueryIndex queryIndex, String searchValue) {
        System.out.println(queryIndex.getQuery());

        try {
            ArrayList<String> foundValue = new ArrayList<String>();

            switch (queryType) {
                case BOOK: {
                    try {
                        for (Book book : Library.books) {
                            if (queryIndex.getQuery().equals("book_id")) {
                                if (String.valueOf(book.getBookID()).equalsIgnoreCase(searchValue)) {
                                    foundValue.add(book.getBookTitle());
                                }
                            } else if (queryIndex.getQuery().equals("book_title")) {
                                if (Pattern.compile(".*" + searchValue + ".*", Pattern.CASE_INSENSITIVE).matcher(book.getBookTitle()).matches()) {
                                    foundValue.add(String.valueOf(book.getBookID()));
                                }
                            } else if (queryIndex.getQuery().equals("book_author")) {
                                if (Pattern.compile(".*" + searchValue + ".*", Pattern.CASE_INSENSITIVE).matcher(book.getAuthor()).matches()) {
                                    foundValue.add(String.valueOf(book.getBookID()));
                                }
                            } else if (queryIndex.getQuery().equals("book_genre")) {
                                if (Pattern.compile(".*" + searchValue + ".*", Pattern.CASE_INSENSITIVE).matcher(book.getGenre()).matches()) {
                                    foundValue.add(String.valueOf(book.getBookID()));
                                }
                            } else {
                                throw new Exception("Unexpected queryName for chosen queryType");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case USER: {
                    try {
                        for (User user : Library.users) {
                            if (queryIndex.getQuery().equals("user_id")) {
                                if (String.valueOf(user.getID()).equalsIgnoreCase(searchValue)) {
                                    foundValue.add(user.getName());
                                }
                            } else if (queryIndex.getQuery().equals("user_name")) {
                                if (user.getName().equalsIgnoreCase(searchValue)) {
                                    foundValue.add(String.valueOf(user.getID()));
                                }
                            } else if (queryIndex.getQuery().equals("user_type")) {
                                if (user.getType().equalsIgnoreCase(searchValue)) {
                                    foundValue.add(user.getName());
                                }
                            } else {
                                throw new Exception("Unexpected queryName for chosen queryType");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                // may be redundant if all getBy() calls are hard coded and not user-dependent
                default:
                    throw new Exception("Invalid QueryType");
            }

            return foundValue;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

            return new ArrayList<String>();
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

    private static ArrayList<Rating> getRatingsByBookID(int bookID) {
        ArrayList<Rating> queryResult = new ArrayList<Rating>();
        for (Rating rating : ratings) {
            if (rating.bookID == bookID) {
                queryResult.add(rating);
            }
        }
        return queryResult;
    }
}
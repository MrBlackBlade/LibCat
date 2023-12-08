package libcat.util;

import libcat.Admin;

import javax.swing.*;
import javax.xml.crypto.AlgorithmMethod;
import java.io.File;
import java.util.ArrayList;

import libcat.util.QueryIndex;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Book> books;
    public static ArrayList<Rating> ratings;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;

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
                        new ImageIcon(row[9])
                ));
            }
        }

        public static ArrayList<String> getBy(QueryType queryType, QueryIndex queryIndex, String searchValue) {
            System.out.println(queryIndex.getQuery());

            try {
                ArrayList<String[]> importedData;
                ArrayList<String> foundValue = new ArrayList<String>();

                switch (queryType) {
                    case BOOK: {
                        importedData = FileSystemManager.query(FileSystemManager.booksFile);

                        try {
                            for (String[] row : importedData) {
                                if (queryIndex.getQuery().equals("book_id")) {
                                    if (row[0].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[1]);
                                    }
                                } else if (queryIndex.getQuery().equals("book_title")) {
                                    if (row[1].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[0]);
                                    }
                                } else if (queryIndex.getQuery().equals("book_author")) {
                                    if (row[2].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[0]);
                                    }
                                }
                                else if (queryIndex.getQuery().equals("book_genre")) {
                                    if (row[3].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[0]);
                                    }
                                }
                                else {
                                    throw new Exception("Unexpected queryName for chosen queryType");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        break;
                    }

                    case USER: {
                        importedData = FileSystemManager.query(FileSystemManager.usersDataFile);

                        try {
                            for (String[] row : importedData) {
                                if (queryIndex.getQuery().equals("user_id")) {
                                    if (row[0].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[1]);
                                    }
                                } else if (queryIndex.getQuery().equals("user_name")) {
                                    if (row[1].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[0]);
                                    }
                                } else if (queryIndex.getQuery().equals("user_type")) {
                                    if (row[2].equalsIgnoreCase(searchValue)) {
                                        foundValue.add(row[1]);
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
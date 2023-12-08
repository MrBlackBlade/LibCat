package libcat.util;

import libcat.Admin;

import javax.xml.crypto.AlgorithmMethod;
import java.io.File;
import java.util.ArrayList;

import libcat.util.QueryIndex;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Book> books;
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
            books.add(new Book(Integer.parseInt(row[0]), row[1], row[2], row[3], row[4], new float[]{Float.parseFloat(row[5])}, Double.parseDouble(row[6]), Double.parseDouble(row[7]), Boolean.parseBoolean(row[8])));
        }
    }

    public static String getBy(QueryType queryType, QueryIndex queryIndex, String searchValue) {
        System.out.println(queryIndex.getQuery());

        try {
            ArrayList<String[]> importedData;
            String foundValue = new String("NOT FOUND");

            switch (queryType) {
                case BOOK: {
                    importedData = FileSystemManager.query(FileSystemManager.booksFile);

                    try {
                        if (queryIndex.getQuery().equals("book_id")) {
                            foundValue = importedData.get(0)[Math.max(0, Algorithm.search(importedData.get(0), searchValue))];
                        } else if (queryIndex.getQuery().equals("book_title")) {
                            foundValue = importedData.get(1)[Algorithm.search(importedData.get(1), searchValue)];
                        } else if (queryIndex.getQuery().equals("book_author")) {
                            foundValue = importedData.get(2)[Algorithm.search(importedData.get(2), searchValue)];
                        } else {
                            throw new Exception("Unexpected queryName for chosen queryType");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case USER: {
                    importedData = FileSystemManager.query(FileSystemManager.usersDataFile);

                    try {
                        if (queryIndex.getQuery().equals("user_id")) {
                            foundValue = importedData.get(0)[Algorithm.search(importedData.get(0), searchValue)];
                        } else if (queryIndex.getQuery().equals("user_name")) {
                            foundValue = importedData.get(1)[Algorithm.search(importedData.get(1), searchValue)];
                        } else if (queryIndex.getQuery().equals("user_type")) {
                            foundValue = importedData.get(2)[Algorithm.search(importedData.get(2), searchValue)];
                        } else {
                            throw new Exception("Unexpected queryName for chosen queryType");
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
        } catch (
                Exception e) {
            System.out.println("Error: " + e.getMessage());

            return "NOT FOUND";
        }
    }
}
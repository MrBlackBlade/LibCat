package libcat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import libcat.util.*;

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
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = (new ArrayList<>(admins));
        users.addAll(customers);
        users.addAll(borrowers);
        return users;
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

    public static ArrayList<Book> sortByRating(BookQueryIndex queryIndex) {
        ArrayList<Book> sortedBooks = new ArrayList<>(Library.books);
        ArrayList<Book> positiveList = new ArrayList<>();
        ArrayList<Book> negativeList = new ArrayList<>();
        ArrayList<Book> neutralList = new ArrayList<>();

        switch (queryIndex.getQuery()) {
            case "book_author": {
                System.out.println("In author");
                sortedBooks.sort((bookX, bookY) -> bookX.getAuthor().compareToIgnoreCase(bookY.getAuthor()));
                break;
            }

            case "book_genre": {
                System.out.println("In genre");
                sortedBooks.sort((bookX, bookY) -> bookX.getGenre().compareToIgnoreCase(bookY.getGenre()));
                break;
            }

            case "book_title": {
                System.out.println("In title");
                sortedBooks.sort((bookX, bookY) -> bookX.getBookTitle().compareToIgnoreCase(bookY.getBookTitle()));
                break;
            }

            case "book_id" : {
                System.out.println("In id");
                sortedBooks.sort((bookX, bookY) -> Integer.compare(bookX.getBookID(), bookY.getBookID()));
                break;
            }
        }

        for (Book book : sortedBooks) {
            if (book.getRating() >= 50.F) {
                positiveList.add(book);
            } else if (book.getRating() >= 0.F) {
                negativeList.add(book);
            } else {
                neutralList.add(book);
            }
        }

        positiveList.sort((bookX, bookY) -> Float.compare(bookY.getRating(), bookX.getRating()));
        negativeList.sort((bookX, bookY) -> Float.compare(bookY.getRating(), bookX.getRating()));

        sortedBooks.clear();
        sortedBooks.addAll(positiveList);
        sortedBooks.addAll(negativeList);
        sortedBooks.addAll(neutralList);

        return sortedBooks;
    }

    private static boolean isLike(String lhs, String rhs) {
        return Pattern.compile(".*" + rhs + ".*", Pattern.CASE_INSENSITIVE).matcher(lhs).matches();
    }

    public static <T> ArrayList<T> getBy(QueryType queryType, QueryIndex queryIndex, String searchValue) {

        ArrayList<T> foundValue = new ArrayList<T>();

        try {
            switch (queryType) {
                case BOOK: {
                    try {
                        for (Book book : Library.books) {
                            if ((queryIndex.getQuery().equals("book_id") && String.valueOf(book.getBookID()).equalsIgnoreCase(searchValue))
                                    || (queryIndex.getQuery().equals("book_title") && isLike(book.getBookTitle(), searchValue))
                                    || (queryIndex.getQuery().equals("book_author") && isLike(book.getAuthor(), searchValue))
                                    || (queryIndex.getQuery().equals("book_genre") && isLike(book.getGenre(), searchValue))
                            ) {
                                foundValue.add((T) book);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                }

                case USER: {
                    try {
                        for (User user : Library.getUsers()) {
                            if (queryIndex.getQuery().equals("user_id") && String.valueOf(user.getID()).equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("user_name") && user.getName().equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("user_type") && user.getType().equalsIgnoreCase(searchValue)
                            ) {
                                foundValue.add((T) user);
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

            return new ArrayList<T>();
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
            if (rating.getBookID() == bookID) {
                queryResult.add(rating);
            }
        }
        return queryResult;
    }
}
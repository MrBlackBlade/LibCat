package libcat;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.Pattern;

import libcat.util.*;

public class Library {
    public static ArrayList<Admin> admins;
    public static ArrayList<Book> books;
    public static ArrayList<Rating> ratings;
    public static ArrayList<Customer> customers;
    public static ArrayList<Borrower> borrowers;
    public static ArrayList<User> users;
    private static ArrayList<Order> orders;
    private static ArrayList<Transaction> transactions;

    public enum QueryType {
        BOOK, USER, ORDER, TRANSACTION, RATING,

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
        }, BOOK_QUERY_INDEX_MAX {
            @Override
            public String getQuery() {
                return null;
            }
        },
    }

    public enum UserQueryIndex implements QueryIndex {
        ID {
            @Override
            public String getQuery() {
                return "user_id";
            }
        }, NAME_EQUAL {
            @Override
            public String getQuery() {
                return "user_name_equal";
            }
        }, NAME_LIKE {
            @Override
            public String getQuery() {
                return "user_name_like";
            }
        }, TYPE {
            @Override
            public String getQuery() {
                return "user_type";
            }
        }, USER_QUERY_INDEX_MAX {
            @Override
            public String getQuery() {
                return null;
            }
        },
    }

    public enum OrderQueryIndex implements QueryIndex {
        ORDER_ID {
            @Override
            public String getQuery() {
                return "order_id";
            }
        }, USER_ID {
            @Override
            public String getQuery() {
                return "user_id";
            }
        }, BOOK_ID {
            @Override
            public String getQuery() {
                return "book_id";
            }
        }, ORDER_QUERY_INDEX_MAX {
            @Override
            public String getQuery() {
                return null;
            }
        },
    }

    public enum TransactionQueryIndex implements QueryIndex {
        TRANSACTION_ID {
            @Override
            public String getQuery() {
                return "transaction_id";
            }
        }, USER_ID {
            @Override
            public String getQuery() {
                return "user_id";
            }
        }, BOOK_ID {
            @Override
            public String getQuery() {
                return "book_id";
            }
        }, TRANSACTION_QUERY_INDEX_MAX {
            @Override
            public String getQuery() {
                return null;
            }
        },
    }

    public static void initialize() {
        FileSystemManager.initFile(FileSystemManager.usersCredsFile);
        FileSystemManager.initFile(FileSystemManager.ordersFile);
        FileSystemManager.initFile(FileSystemManager.transactionsFile);

        books = new ArrayList<Book>();
        ratings = new ArrayList<Rating>();
        admins = new ArrayList<Admin>();
        customers = new ArrayList<Customer>();
        borrowers = new ArrayList<Borrower>();
        orders = new ArrayList<Order>();
        transactions = new ArrayList<Transaction>();

        Library.makeRatings(); //has to be called before books (prolly before users too)
        Library.makeUsers();
        Library.makeBooks();
        Library.makeOrders();
        Library.makeTransactions();
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
                    Boolean.parseBoolean(row[8]),
                    new ImageIcon(FileSystemManager.cwd + row[9])));
        }
    }

    private static void makeOrders() {
        ArrayList<String[]> ordersList = FileSystemManager.query(FileSystemManager.ordersFile);

        for (String[] row : ordersList) {
            orders.add(new Order(
                    Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]),
                    Integer.parseInt(row[2]),
                    Integer.parseInt(row[3]),
                    Double.parseDouble(row[4])
            ));
        }
    }

    private static void makeTransactions() {
        ArrayList<String[]> transactionsList = FileSystemManager.query(FileSystemManager.transactionsFile);

        for (String[] row : transactionsList) {
            transactions.add(new Transaction(
                    Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]),
                    Integer.parseInt(row[2]),
                    row[3]
            ));
        }
    }

    public static void createOrder(Order order) {
        orders.add(order);

        FileSystemManager.updateData(FileSystemManager.ordersFile);
    }

    public static void createTransaction(Transaction transaction) {
        transactions.add(transaction);

        FileSystemManager.updateData(FileSystemManager.transactionsFile);
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = (new ArrayList<>(admins));
        users.addAll(customers);
        users.addAll(borrowers);
        return users;
    }

    public static ArrayList<Order> getOrders() {
        return Library.orders;
    }

    public static ArrayList<Transaction> getTransactions() {
        return Library.transactions;
    }

    public static ArrayList<Order> getOrderHistory(Customer customer) {
        return getBy(QueryType.ORDER, OrderQueryIndex.USER_ID, String.valueOf(customer.getID()));
    }

    public static ArrayList<Transaction> getBorrowHistory(Borrower borrower) {
        return getBy(QueryType.TRANSACTION, OrderQueryIndex.USER_ID, String.valueOf(borrower.getID()));
    }

    public static ArrayList<Book> sortByRating(ArrayList<Book> bookSource, BookQueryIndex queryIndex) {
        ArrayList<Book> sortedBooks = new ArrayList<>(bookSource);
        ArrayList<Book> positiveList = new ArrayList<>();
        ArrayList<Book> negativeList = new ArrayList<>();
        ArrayList<Book> neutralList = new ArrayList<>();

        switch (queryIndex.getQuery()) {
            case "book_author": {
                sortedBooks.sort((bookX, bookY) -> bookX.getAuthor().compareToIgnoreCase(bookY.getAuthor()));
                break;
            }

            case "book_genre": {
                sortedBooks.sort((bookX, bookY) -> bookX.getGenre().compareToIgnoreCase(bookY.getGenre()));
                break;
            }

            case "book_title": {
                sortedBooks.sort((bookX, bookY) -> bookX.getBookTitle().compareToIgnoreCase(bookY.getBookTitle()));
                break;
            }

            case "book_id": {
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

    public static ArrayList<Book> recommendBooks(User user) {
        ArrayList<Book> recommendedBooks = new ArrayList<>();
        ArrayList<Order> userOrderHistory = getOrderHistory((Customer) user);

        try {
            HashMap<String, Integer> genres = new HashMap<>();

            // count the number of books in each genre in the order history
            for (Order order : userOrderHistory) {
                genres.put(order.getBook().getGenre(), (genres.get(order.getBook().getGenre()) == null ? 1 : genres.get(order.getBook().getGenre()) + 1));
            }

            // import all the entries into a list, and sort them descendingly
            List<Map.Entry<String, Integer>> genreEntries = new ArrayList<>(genres.entrySet());
            genreEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

            // re-add the sorted entries back into a linked hashmap to maintain the order
            LinkedHashMap<String, Integer> sortedGenres = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : genreEntries) {
                sortedGenres.put(entry.getKey(), entry.getValue());
            }

            // create a list of the books not found in the user's order history
            ArrayList<Book> newBooks = new ArrayList<>();
            for (Book book : Library.books) {
                if (!userOrderHistory.contains(book)) {
                    newBooks.add(book);
                }
            }

            // add the books in the same genre that aren't in the order history
            for (String genre : sortedGenres.keySet()) {
                for (Book book : sortByRating(newBooks, BookQueryIndex.TITLE)) {
                    if (book.getGenre().equals(genre)) {
                        recommendedBooks.add(book);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return recommendedBooks;
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
                                    || queryIndex.getQuery().equals("user_name_equal") && user.getName().equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("user_name_like") && isLike(user.getName(), searchValue)
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

                case ORDER: {
                    for (Order order : Library.getOrders()) {
                        try {
                            if (queryIndex.getQuery().equals("order_id") && String.valueOf(order.getID()).equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("user_id") && String.valueOf(order.getUser().getID()).equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("book_id") && String.valueOf(order.getBook().getBookID()).equalsIgnoreCase(searchValue)
                            ) {
                                foundValue.add((T) order);
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                    }
                    break;
                }

                case TRANSACTION: {
                    for (Transaction transaction : Library.getTransactions()) {
                        try {
                            if (queryIndex.getQuery().equals("transaction_id") && String.valueOf(transaction.getID()).equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("user_id") && String.valueOf(transaction.getUser().getID()).equalsIgnoreCase(searchValue)
                                    || queryIndex.getQuery().equals("book_id") && String.valueOf(transaction.getBook().getBookID()).equalsIgnoreCase(searchValue)
                            ) {
                                foundValue.add((T) transaction);
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }
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

    public static <T extends Comparable<T>> T getMax(ArrayList<T> array) {
        return Collections.max(array);
    }
}
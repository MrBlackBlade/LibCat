package libcat;

import libcat.util.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemManager {
    protected static final String cwd = new File(Paths.get("").toAbsolutePath().toString()) + "\\resources\\";
    protected static final String usersCredsFile = "userscreds.txt";
    protected static final String usersDataFile = "usersdata.txt";
    protected static final String booksFile = "books.txt";
    protected static final String ratingsFile = "ratings.txt";
    protected static final String ordersFile = "orders.txt";
    protected static final String transactionsFile = "transaction.txt";

    public static String getImageWD() {
        return (cwd + "\\images\\");
    }

    protected static void initFile(String file) {
        try (FileReader fr = new FileReader(cwd + file)) {
            ;
        } catch (IOException ex) {
            try (FileWriter fw = new FileWriter(cwd + file)) {
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    protected static ArrayList<String[]> query(String file) {
        ArrayList rows = new ArrayList<String[]>();
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
            for (int i = 0; i < countLines(file); i++) {
                String row = raf.readLine();
                String[] rowFields = row.split(",");
                rows.add(rowFields);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

    protected static void insertRow(String file, String[] row) {
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
            for (int i = 0; i < countLines(file); i++) {
                raf.readLine();
            }
            String rowString = "";
            for (String element : row) {
                rowString += element;
                rowString += ",";
            }

            rowString = rowString.substring(0, rowString.length() - 1) + "\n";
            raf.writeBytes(rowString);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int countLines(String file) {
        int lines = 0;
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
            for (; raf.readLine() != null; lines++) ;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }

    protected static void updateData(String file) {
        switch (file) {
            case usersDataFile: {
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (User user : Library.getSortedList(Library.getUsers())) {
                    if (user.getType().equalsIgnoreCase("admin")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "admin"
                        });
                    } else if (user.getType().equalsIgnoreCase("borrower")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "borrower"
                        });
                    } else if (user.getType().equalsIgnoreCase("customer")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "customer"
                        });
                    }
                }

                break;
            }
            case booksFile: {
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Book book : Library.getBooks()) { //how do you keep getting sidetracked a7a
                    insertRow(booksFile, new String[]{
                            String.valueOf(book.getID()),
                            String.valueOf(book.getTitle()),
                            String.valueOf(book.getAuthor()),
                            String.valueOf(book.getGenre()),
                            String.valueOf(book.getYear()),
                            String.valueOf(book.getRating()),
                            String.valueOf(book.getBasePrice()),
                            String.valueOf(book.getSalePercent()),
                            String.valueOf(book.getPurchaseStatus().get(Book.Availablity.PURCHASABLE)),
                            String.valueOf(book.getPurchaseStatus().get(Book.Availablity.BORROWABLE)),
                            String.valueOf(book.getImagePath())
                    });
                }

                break;
            }
            case ordersFile: {
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Order order : Library.getOrders()) {
                    insertRow(ordersFile, new String[]{
                            String.valueOf(order.getID()),
                            String.valueOf(order.getUser().getID()),
                            String.valueOf(order.getBook().getID()),
                            String.valueOf(order.getQuantity()),
                            String.valueOf(order.getTotalPrice())
                    });
                }

                break;
            }
            case transactionsFile: {
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Transaction transaction : Library.getTransactions()) {
                    insertRow(transactionsFile, new String[]{
                            String.valueOf(transaction.getID()),
                            String.valueOf(transaction.getUser().getID()),
                            String.valueOf(transaction.getBook().getID()),
                            transaction.getBorrowDate().toString()
                    });
                }

                break;
            }
            case ratingsFile: {
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")) {
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Rating ratings : Library.getRatings()) {
                    insertRow(ratingsFile, new String[]{
                            String.valueOf(ratings.getBook().getID()),
                            String.valueOf(ratings.getCustomer().getID()),
                            String.valueOf(ratings.isLike()),
                            String.valueOf(ratings.getReview())
                    });
                }

                break;
            }
        }
    }

    protected static void updateData() {
        updateData(usersDataFile);
        updateData(booksFile);
        updateData(ordersFile);
        updateData(transactionsFile);
        updateData(ratingsFile);
    }
}

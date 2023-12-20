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
                    insertRow(usersDataFile, user.toStringArray());
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
                    insertRow(booksFile, book.toStringArray());
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
                    insertRow(ordersFile, order.toStringArray());
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
                    insertRow(transactionsFile, transaction.toStringArray());
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
                for (Rating rating : Library.getRatings()) {
                    insertRow(ratingsFile, rating.toStringArray());
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

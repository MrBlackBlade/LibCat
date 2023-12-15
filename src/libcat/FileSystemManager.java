package libcat;

import libcat.util.Borrower;
import libcat.util.Customer;
import libcat.util.User;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    protected static String[] mergeStringArrays(String[] array1, String[] array2) {
        String[] mergedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        return mergedArray;
    }

    protected static void initFile(String file) {
        try (FileReader fr = new FileReader(cwd + file)){;} catch (IOException ex) {
            try (FileWriter fw = new FileWriter(cwd + file)){
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    protected static ArrayList<String[]> query(String file) {
        ArrayList rows = new ArrayList<String[]>();
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")){
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
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")){
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
        try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")){
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
            case usersDataFile:
                try (RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw")){
                    raf.setLength(0);
                    raf.seek(0);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (User user: Library.getUsers()) {
                    if (user.getType().equalsIgnoreCase("admin")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "admin"
                        });
                    }
                    else if (user.getType().equalsIgnoreCase("borrower")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "borrower"
                        });
                    }
                    else if (user.getType().equalsIgnoreCase("customer")) {
                        insertRow(usersDataFile, new String[]{
                                String.valueOf(user.getID()),
                                user.getName(),
                                "customer"
                        });
                    }
                }
                break;
        }
    }
}

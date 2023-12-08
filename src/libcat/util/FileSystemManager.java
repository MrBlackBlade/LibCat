package libcat.util;

import libcat.Main;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemManager {
    public static String cwd = new File(Paths.get("").toAbsolutePath().toString()) + "\\resources\\";

    public static String usersCredsFile = "userscreds.txt";
    public static String usersDataFile = "usersdata.txt";
    public static String booksFile = "books.txt";
    public static String ratingsFile = "ratings.txt";


    public static String[] mergeStringArrays(String[] array1, String[] array2){
        String[] mergedArray = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, mergedArray, array1.length, array2.length);
        return mergedArray;
    }
    public static void initFile(String file) {
        try {
            FileReader fr = new FileReader(cwd + file);
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(cwd + file);
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    public static ArrayList<String[]> query(String file) {
        ArrayList rows = new ArrayList<String[]>();
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw");
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
    public static void insertRow(String file, String[] row) {
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw");
            for (int i = 0; i < countLines(file); i++) {
                raf.readLine();
            }
            String rowString = "";
            for (String element:row) {
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
    public static int countLines(String file) {
        int lines = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw");
            for (; raf.readLine() != null; lines++);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
}

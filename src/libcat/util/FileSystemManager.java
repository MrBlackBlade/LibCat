package libcat.util;

import libcat.Main;

import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemManager {
    static String cwd = new File(Paths.get("").toAbsolutePath().toString()) + "\\resources\\";
    public static String usersFile = "userscreds.txt";

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
    public static void insertRow(String file, String[] row) {
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + file, "rw");
            for (int i = 0; i < countLines(usersFile); i++) {
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
            for (int i = 0; raf.readLine() != null; i++) {
                lines++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
}

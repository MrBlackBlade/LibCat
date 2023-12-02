package libcat.util;

import libcat.Main;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemManager {
    Path cwd = Paths.get("").toAbsolutePath();
    File f = new File(cwd.toString());
    int line;
    public void readFile() {
        try {
            FileReader fr = new FileReader(f + "\\resources\\users.txt");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(f + "\\resources\\users.txt");
                System.out.println("File created");
            } catch (IOException ex1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        countLines();
    }
    public void addData(String user, String password) {
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\resources\\users.txt", "rw");
            for (int i = 0; i < line; i++) {
                raf.readLine();
            }
            raf.writeBytes(user + "\n");
            raf.writeBytes(password + "\n");
            raf.writeBytes("\n");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void countLines() {
        try {
            line = 0;
            RandomAccessFile raf = new RandomAccessFile(f +"\\resources\\users.txt", "rw");
            for (int i = 0; raf.readLine() != null; i++) {
                line++;
            }
            //System.out.println("number of lines:" + line);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void checkForUser(String usr, String pswd) {
        boolean user_found = false;
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\resources\\users.txt", "rw");
            for (int i = 0; i < line; i += 2) {
                //System.out.println("count " + i);

                String forUser = raf.readLine();
                String forPswd = raf.readLine();

                if (usr.equals(forUser) && pswd.equals(forPswd)) {
                    user_found = true;
                    raf.readLine();
                    break;
                } else {
                    raf.readLine();
                }
            }
            if(user_found){
                // Main window will be added here
                JOptionPane.showMessageDialog(null, "Welcome!");
            }
            else {
                //delete system32
                JOptionPane.showMessageDialog(null, "User not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

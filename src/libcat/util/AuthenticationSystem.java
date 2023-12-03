package libcat.util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AuthenticationSystem extends FileSystemManager{
    public static boolean credentialsMatch(String usr, String pswd) {
        boolean credentialsMatch = false;
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + usersFile, "rw");
            for (int i = 0; i < countLines(usersFile); i++) {
                String record = raf.readLine();
                String[] recordFields = record.split(",");
                String forUser = recordFields[1];
                String forPswd = recordFields[2];

                if (usr.equals(forUser) && pswd.equals(forPswd)) {
                    credentialsMatch = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentialsMatch;
    }
    public static boolean userExists(String usr) {
        boolean userExists = false;
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + usersFile, "rw");
            for (int i = 0; i < countLines(usersFile); i++) {
                String record = raf.readLine();
                String[] recordFields = record.split(",");
                String forUser = recordFields[1];
                if (usr.equals(forUser)) {
                    userExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userExists;
    }
    public static void insertNewRegister(String[] row) {
        insertRow(usersFile, row);
    }
    public static boolean registerNewUser(String[] row) {
        if(userExists(row[1])) {
            return false;
        } else {
            insertRow(usersFile, row);
            return true;
        }
    }
}

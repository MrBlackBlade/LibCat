package libcat.util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AuthenticationSystem extends FileSystemManager{
    public static boolean credentialsMatch(String usr, String pswd) {
        boolean credentialsMatch = false;
        try {
            RandomAccessFile raf = new RandomAccessFile(cwd + usersCredsFile, "rw");
            for (int i = 0; i < countLines(usersCredsFile); i++) {
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
            RandomAccessFile raf = new RandomAccessFile(cwd + usersCredsFile, "rw");
            for (String[] rowFields: query(usersCredsFile)) {
                if (usr.equals(rowFields[1])) {
                    userExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userExists;
    }
    public static boolean registerNewUser(String[] row) {
        String[] userid = {getLastID(usersCredsFile)};
        row = mergeStringArrays(userid,row);
        if(userExists(row[1])) {
            return false;
        } else {
            insertRow(usersCredsFile, row);
            insertRow(usersDataFile, new String[]{row[0], row[1], "customer"});
            // another function will be added to update users
            Library.initialize();
            return true;
        }
    }
    public static String getLastID(String file) {
        return String.valueOf(Integer.parseInt(query(file).get(query(file).size()-1)[0])+1);
    }
}

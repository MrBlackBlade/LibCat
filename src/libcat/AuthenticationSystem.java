package libcat;

import libcat.util.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AuthenticationSystem extends FileSystemManager {
    protected static boolean credentialsMatch(String usr, String pswd) {
        boolean credentialsMatch = false;
        for (String[] row: query(usersCredsFile)) {
            if (usr.equals(row[1]) && pswd.equals(row[2])) {
                credentialsMatch = true;
                break;
            }
        }
        return credentialsMatch;
    }

    private static boolean userExists(String user) {
        boolean userExists = false;
        for (String[] rowFields: query(usersCredsFile)) {
            if (user.equalsIgnoreCase(rowFields[1])) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }

    protected static boolean registerNewUser(String[] row) {
        int userid = getMax(Library.getUsers(), (userX, userY) -> Math.max(userX.getID(), userY.getID())).getID() + 1;

        row = mergeStringArrays(new String[]{String.valueOf(userid)},row);
        if(userExists(row[1])) {
            return false;
        } else {
            insertRow(usersCredsFile, row);
            Admin.addCustomer(userid, row[1]);
            updateData(usersDataFile);
            return true;
        }
    }

    private static <T> T getMax(ArrayList<T> array, Comparator<T> comparator) {
        return Collections.max(array, comparator);
    }
}

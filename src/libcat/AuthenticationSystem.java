package libcat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    protected static boolean userExists(String user) {
        boolean userExists = false;
        for (String[] rowFields: query(usersCredsFile)) {
            if (user.equalsIgnoreCase(rowFields[1])) {
                userExists = true;
                break;
            }
        }
        return userExists;
    }

    protected static void registerNewUser(String[] row) {
        int userid = Integer.parseInt(getLastID(usersCredsFile));
        row = mergeStringArrays(new String[]{String.valueOf(userid)},row);
        insertRow(usersCredsFile, row);
        Admin.addCustomer(userid, row[1]);
        updateData(usersDataFile);
    }

    private static String getLastID(String file) {
        return String.valueOf(Integer.parseInt(query(file).get(query(file).size()-1)[0])+1);
    }
    protected static boolean usernameValid(String username){
        String regex = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(username);

        return !matcher.find();
    }
    protected static boolean passwordValid(String password){
        String regex = "[^a-zA-Z0-9!@#$%&*]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        return !matcher.find();
    }
}

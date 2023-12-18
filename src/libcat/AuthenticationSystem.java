package libcat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libcat.util.User;

public class AuthenticationSystem extends FileSystemManager {
    protected static class Validation {
        protected static HashMap<String, Boolean> getUsernameValidations(String username) {
            HashMap<String, Boolean> userValidations = new HashMap<>();
            boolean pass = true;

            userValidations.put("Username taken", userExists(username));
            userValidations.put("Username cannot contain special characters", usernameInvalid(username));
            userValidations.put("Username needs to be 4-20 characters long", notInLength(username, 4,20));

            for (String key: userValidations.keySet()) {
                pass = !userValidations.get(key) && pass;
            }

            userValidations.put("AllChecksPass", pass);

            return userValidations;
        }
        protected static HashMap<String, Boolean> getPasswordValidations(String password) {
            HashMap<String, Boolean> passwordValidations = new HashMap<>();
            boolean pass = true;

            passwordValidations.put("Password cannot contain special characters", passwordInvalid(password));
            passwordValidations.put("Password needs to be 8-32 characters long", notInLength(password, 8,32));

            for (String key: passwordValidations.keySet()) {
                pass = !passwordValidations.get(key) && pass;
            }

            passwordValidations.put("AllChecksPass", pass);
            return passwordValidations;
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
        protected static boolean usernameInvalid(String username){
            String regex = "[^a-zA-Z0-9]";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(username);

            return matcher.find();
        }
        protected static boolean notInLength(String string, int min, int max) {
            return (string.length() > max || string.length() < min);
        }
        protected static boolean passwordInvalid(String password){
            String regex = "[^a-zA-Z0-9!@#$%&*]";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(password);

            return matcher.find();
        }
    }
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

    protected static void registerNewUser(String[] row) {
        int userid = Library.getMax(Library.getUsers()).getID() + 1;
        row = mergeStringArrays(new String[]{String.valueOf(userid)},row);
        insertRow(usersCredsFile, row);
        Admin.addCustomer(userid, row[1]);
    }
}

package libcat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libcat.util.Customer;
import libcat.util.User;

public class AuthenticationSystem
		extends FileSystemManager {
	protected static class Validation {
		protected static HashMap<String, Boolean> getUsernameValidations(String username) {
			HashMap<String, Boolean> userValidations = new HashMap<>();
			boolean pass = true;

			userValidations.put("Username taken", userExists(username));
			userValidations.put("Username cannot contain special characters", usernameInvalid(username));
			userValidations.put("Username needs to be 4-20 characters long", notInLength(username, 4, 20));

			for (String key : userValidations.keySet()) {
				pass = !userValidations.get(key) && pass;
			}

			userValidations.put("AllChecksPass", pass);

			return userValidations;
		}

		protected static HashMap<String, Boolean> getPasswordValidations(String password) {
			HashMap<String, Boolean> passwordValidations = new HashMap<>();
			boolean pass = true;

			passwordValidations.put("Password cannot contain special characters", passwordInvalid(password));
			passwordValidations.put("Password needs to be 8-32 characters long", notInLength(password, 8, 32));

			for (String key : passwordValidations.keySet()) {
				pass = !passwordValidations.get(key) && pass;
			}

			passwordValidations.put("AllChecksPass", pass);
			return passwordValidations;
		}

		protected static HashMap<String, Boolean> getPhoneNumberValidations(String phoneNumber) {
			HashMap<String, Boolean> phoneNumberValidations = new HashMap<>();
			boolean pass = true;

			phoneNumberValidations.put("Phone number is not valid", phoneNumberInvalid(phoneNumber));

			for (String key : phoneNumberValidations.keySet()) {
				pass = !phoneNumberValidations.get(key) && pass;
			}

			phoneNumberValidations.put("AllChecksPass", pass);

			return phoneNumberValidations;
		}

		protected static HashMap<String, Boolean> getEmailValidations(String email) {
			HashMap<String, Boolean> emailValidations = new HashMap<>();
			boolean pass = true;

			emailValidations.put("E-mail is not valid", emailInvalid(email));

			for (String key : emailValidations.keySet()) {
				pass = !emailValidations.get(key) && pass;
			}

			emailValidations.put("AllChecksPass", pass);

			return emailValidations;
		}

		protected static boolean userExists(String username) {
			boolean userExists = false;
			for (User user : Library.getUsers()) {
				if (username.equalsIgnoreCase(user.getName())) {
					userExists = true;
				}
			}
//            for (String[] rowFields : query(usersCredsFile)) {
//                if (username.equalsIgnoreCase(rowFields[1])) {
//                    userExists = true;
//                    break;
//                }
//            }
			return userExists;
		}

		protected static boolean usernameInvalid(String username) {
			String regex = "[^a-zA-Z0-9]";

			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(username);

			return matcher.find();
		}

		protected static boolean notInLength(String string, int min, int max) {
			return (string.length() > max || string.length() < min);
		}

		protected static boolean passwordInvalid(String password) {
			String regex = "[^a-zA-Z0-9!@#$%&*]";

			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(password);

			return matcher.find();
		}

		protected static boolean phoneNumberInvalid(String phoneNumber) {
			String regex = "^(\\+201|01|00201)[0-2,5]{1}[0-9]{8}";

			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(phoneNumber);
			System.out.println(matcher.matches());
			return !matcher.matches();
		}

		protected static boolean emailInvalid(String email) {
			String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(email);
			return !matcher.matches();
		}
	}

	protected static boolean credentialsMatch(String username, String password) {
		boolean credentialsMatch = false;
		for (User user : Library.getUsers()) {
			if (user.getName().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
				credentialsMatch = true;
				break;
			}
		}
//        for (String[] row: query(usersCredsFile)) {
//            if (username.equals(row[1]) && password.equals(row[2])) {
//                credentialsMatch = true;
//                break;
//            }
//        }
		return credentialsMatch;
	}

	protected static void registerNewUser(String[] row) {
		int userid = Library.getMax(Library.getUsers()).getID() + 1;
		row = Library.mergeStringArrays(new String[] { String.valueOf(userid) }, row);
		//insertRow(usersCredsFile, row);
		Admin.addCustomer(userid, row[1], row[2], row[3], row[4]);
	}
}

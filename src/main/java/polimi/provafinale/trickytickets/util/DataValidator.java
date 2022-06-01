package polimi.provafinale.trickytickets.util;

import java.util.Date;

// Classe che verifica la correttezza dei dati inseriti dall'utente (date, email etc)

public class DataValidator {

	public static boolean isName(String val) {

		String name = "^[A-Za-z ]*$";

		if (val.matches(name)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPassword(String val) {
		
		String passregex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\S])[A-Za-z0-9\\S]{6,12}$";

		if (val.matches(passregex)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPhoneNo(String val) {
		
		String regex = "^[0-9]+";
		
		if (val.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNull(String val) {
		
		if (val == null || val.trim().length() == 0) {
			
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isDate(String val) {
		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

}

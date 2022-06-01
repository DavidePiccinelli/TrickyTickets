package polimi.provafinale.trickytickets.util;

import java.util.ResourceBundle;

/* classe di supporto relizzata per accedere alle informazioni 
 * cumulate in system.properties, principalmente per generalizzare i messaggi di errore 
 * e facilitare il riutilizzo delle impostazioni per l'invio di mail  */

public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("system");

	public static String getValue(String key) {
		String val = null;
		try {
			val = rb.getString(key);
		} catch (Exception e) {
			val = key;
		}
		return val;
	}
	
	public static String getSmtp() {		
		String val = rb.getString("smtp.server");
		return val;		
	}
	
	public static String getPageSize() {		
		String val = rb.getString("page.size");
		return val;		
	}
	public static String getPort() {		
		String val = rb.getString("smtp.port");
		return val;		
	}
	
	public static String getLogin() {		
		String val = rb.getString("email.login");
		return val;		
	}

	public static String getPwd() {		
		String val = rb.getString("email.pwd");
		return val;		
	}

	public static String getValue(String key, String param) {
		String msg = getValue(key);
		msg = msg.replace("{0}", param);
		return msg;
	}

	public static String getValue(String key, String[] params) {
		String msg = getValue(key);
		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}
		return msg;
	}
	
}

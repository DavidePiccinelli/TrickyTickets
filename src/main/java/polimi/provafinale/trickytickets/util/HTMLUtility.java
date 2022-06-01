package polimi.provafinale.trickytickets.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import polimi.provafinale.trickytickets.bean.BaseBean;

//Classe di supporto tilizzata per creare il menu a tendina per la scelta del ruolo aziendale, dello stato del ticket e della categoria del ticket

public class HTMLUtility {

	public static String getList(String name, String selectedVal,HashMap<String, String> map) {
		
	
		StringBuffer sb = new StringBuffer(	"<select class='form-control bd' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;
		String select = "-----Seleziona-----";
		sb.append("<option selected value='" + select + "'>" + select+ "</option>");
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {

				sb.append("<option selected value='" + key + "'>" + val
						+ "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	
public static String getList(String name, String selectedVal, List<?> list) {
		
	    @SuppressWarnings("unchecked")	
		List<BaseBean> dd = (List<BaseBean>) list;

		StringBuffer sb = new StringBuffer("<select class='form-control bd' name='" + name + "' id='" + name+ "'>");
		
		String key = null;
		
		String val = null;
		
		String select = "-----Seleziona-----";
		
		sb.append("<option selected value='" + select + "'>" + select+ "</option>");
		
		for (BaseBean obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val+ "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}

		sb.append("</select>");
		return sb.toString();
	}

}

package controller.login;

import java.util.HashMap;

public class SystemNames {
	private static String k06 = "K06 Konsolidierung PIA / AM";
	private static String k16 = "K16 Konsolidierung PIA Marktstufe Asien Pazifik";
	private static String k17 = "K17 Konsolidierung PIA Marktstufe Amerika";
	private static String k18 = "K18 Konsolidierung PIA Marktstufe Europa";

	private static String p06 = "P06 Produktion PIA / AM";
	private static String p16 = "P16 Produktion PIA Marktstufe Asien Pazifik";
	private static String p17 = "P17 Produktion PIA Marktstufe Amerika";
	private static String p18 = "P18 Production PIA Marktstufe Europa";

	private static HashMap<String, String> namesHashMap = new HashMap<String, String>();

	private static boolean ifFill = false;

	/**
	 * @param systemName shortcut of a system name
	 * @return complete name from a shortcut
	 * @throws SystemNameException
	 */
	public static String getSystemName(String systemName)
			throws SystemNameException {
		if (!ifFill)
			fillHashMap();
		// if the input of system name is not valid, throw a Exception
		if (namesHashMap.get(systemName) == null)
			throw new SystemNameException();
		return namesHashMap.get(systemName);
	}

	/**
	 * set complete names to shortcuts
	 */
	private static void fillHashMap() {
		namesHashMap.put("k06", k06);
		namesHashMap.put("K06", k06);
		namesHashMap.put("k16", k16);
		namesHashMap.put("K16", k16);
		namesHashMap.put("k17", k17);
		namesHashMap.put("K17", k17);
		namesHashMap.put("k18", k18);
		namesHashMap.put("K18", k18);
		
		namesHashMap.put("p06", p06);
		namesHashMap.put("P06", p06);
		namesHashMap.put("p16", p16);
		namesHashMap.put("P16", p16);
		namesHashMap.put("p17", p17);
		namesHashMap.put("P17", p17);
		namesHashMap.put("p18", p18);
		namesHashMap.put("P18", p18);
		
		namesHashMap.put("6", "k06");
		namesHashMap.put("16", "k16");
		namesHashMap.put("17", "k17");
		namesHashMap.put("18", "k18");
		
		ifFill = true;
	}
}

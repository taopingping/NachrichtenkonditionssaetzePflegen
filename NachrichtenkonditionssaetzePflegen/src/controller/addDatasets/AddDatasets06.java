package controller.addDatasets;

import controller.general.RunScript;
import controller.general.WindowsManagement;

public class AddDatasets06 {

	private final static int NumberOfShowedRows_ReadActions = 30;
	private final static int NumberOfstartedRow_ReadActions = 5;

	public static Process fillVariableToNach(String plant, String kschl,
			int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtI2-LOW\").text = \"rv\" \n"
				+ "session.findById(\"wnd[0]/usr/txtI4-LOW\").text = \"*"
				+ plant + "*\" \n"
				+ "session.findById(\"wnd[0]/usr/ctxtI3-LOW\").text = \""
				+ kschl + "\" \n" + "\n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press \n";
		return RunScript.runWScript(code);
	}

	public static String getNumberOfRows(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "Wscript.Echo session.findById(\"wnd[0]\").text";
		Process p = RunScript.runCScript(code);
		return RunScript.getSingleValue(p);
	}

	public static String[] readActions(int numberOfActions, int connection,
			int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr\").verticalScrollbar.position = 0 \n";
		for (int i = NumberOfstartedRow_ReadActions; i <= NumberOfShowedRows_ReadActions; i++) {
			code += "Wscript.Echo session.findById(\"wnd[0]/usr/lbl[71," + i
					+ "]\").text \n";
		}
		for (int ScrollDownRow = 1; ScrollDownRow <= numberOfActions
				- (NumberOfShowedRows_ReadActions
						- NumberOfstartedRow_ReadActions + 1); ScrollDownRow++) {
			code += "session.findById(\"wnd[0]/usr\").verticalScrollbar.position = "
					+ ScrollDownRow + " \n";
			code += "Wscript.Echo session.findById(\"wnd[0]/usr/lbl[71,"
					+ NumberOfShowedRows_ReadActions + "]\").text \n";
		}
		Process p = RunScript.runCScript(code);
		String[] actions = RunScript.getValue(p);
		return onlyActions(actions);
	}

	public static Process tableNach(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtPA_DBTAB\").text = \"nach\" \n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press";
		return RunScript.runWScript(code);
	}

	public static Process setListWidth(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtLIST_BRE\").text = \"180\" \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0";
		return RunScript.runWScript(code);
	}

	public static Process insertConditionType(String kschl, int connection,
			int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtRV13B-KSCHL\").text = \""
				+ kschl + "\" \n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[17]\").press \n"
				+ "session.findById(\"wnd[1]/tbar[0]/btn[0]\").press";
		return RunScript.runWScript(code);
	}

	public static Process insertAction(String action, int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtF001\").text = \""
				+ action
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/ctxtF002-LOW\").text = \"\" \n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press \n";
		return RunScript.runWScript(code);
	}

	public static boolean fillDataset(String importer, String parNr,
			int connection, int window) throws InterruptedException {
		String code = WindowsManagement.start(connection, window)
				+"Dim lastRow \n"
				+"lastRow = "+AddDatasets.getNumberOfShowedRows()+" \n"
				+ RunScript.readScript("addDatasets06/readImporters.txt");
		String[] importers = RunScript.getValue(RunScript.runCScript(code));
		if (exist(importers, importer)) {
			System.out.println("Importeur existiert bereit im System.");
			return false;
		}
		int scroll = 0;
		int row = 0;
		if (importers.length < AddDatasets.getNumberOfShowedRows()) {
			scroll = 0;
			row = importer.length();
		} else {
			scroll = importers.length - AddDatasets.getNumberOfShowedRows() + 1;
			row = AddDatasets.getNumberOfShowedRows() - 1;
		}
		RunScript.scrollDown(scroll, connection, window);
		addImporter(row, importer, parNr,
				connection, window).waitFor();
		return true;
	}

	public static Process back(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[0]/btn[3]\").press";
		return RunScript.runWScript(code);
	}

	private static Process addImporter(int row, String importer, String parNr,
			int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtKOMB-KUNNR[0,"
				+ row
				+ "]\").text = \""
				+ importer
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtRV13B-PARNR[3,"
				+ row + "]\").text = \"" + parNr + "\" \n";
		return RunScript.runWScript(code);
	}

	private static boolean exist(String[] importers, String importer) {
		for (int i = 0; i < importers.length; i++) {
			if (importer.equals(importers[i]))
				return true;
		}
		return false;
	}

	private static String[] onlyActions(String[] temp) {
		String[] result = new String[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = "";
			for (int i2 = 0; i2 < 4; i2++) {
				result[i] += temp[i].charAt(i2);
			}
		}
		return result;
	}

}

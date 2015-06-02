package controller.editPAR;

import controller.general.Processes;
import controller.general.RunScript;
import controller.general.WindowsManagement;

public class EditPAR implements Processes {

	/**
	 * set kschl and vakey value to filter for table to show
	 * 
	 * @param kschl
	 * @param vakey
	 * @return scripting process
	 */
	static public Process setFilter(String kschl, String vakey, int connection,
			int window) {
		String filter = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/tblSAPLSE16NSELFIELDS_TC/ctxtGS_SELFIELDS-LOW[2,7]\").text = \""
				+ kschl
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPLSE16NSELFIELDS_TC/ctxtGS_SELFIELDS-LOW[2,8]\").text = \"*"
				+ vakey
				+ "*\" \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPLSE16NSELFIELDS_TC/ctxtGS_SELFIELDS-LOW[2,8]\").setFocus \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPLSE16NSELFIELDS_TC/ctxtGS_SELFIELDS-LOW[2,8]\").caretPosition = 5 \n"
				+ "session.findById(\"wnd[0]\").sendVKey 8 \n";
		return RunScript.runWScript(filter);
	}

	/**
	 * @return the number of found data sets
	 * @throws NullPointerException
	 *             the process was not successful
	 */
	static public String getNumberOfHits(int connection, int window)
			throws NullPointerException {
		String code = WindowsManagement.start(connection, window)
				+ "Wscript.Echo session.findById(\"wnd[0]/usr/txtGD-NUMBER\").text";
		Process p = RunScript.runCScript(code);
		return RunScript.getSingleValue(p);
	}

	/**
	 * set NACH as table name
	 * 
	 * @return scripting process
	 */
	static public Process insertTableNACH(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtGD-TAB\").text = \"NACH\" \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0";
		return RunScript.runWScript(code);
	}

	/**
	 * call debugging modus
	 * 
	 * @return scripting process
	 */
	static public Process debugModus(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[0]/okcd\").text = \"/h\" \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0 \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0";
		return RunScript.runWScript(code);
	}

	/**
	 * fill variables to change debugging modus
	 * 
	 * @return scripting process
	 */
	static public Process fillVariables(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ RunScript.readScript("editPAR/fillVariables.txt");
		return RunScript.runWScript(code);
	}

	/**
	 * show a ready set table
	 * 
	 * @return scripting process
	 */
	static public Process showTable(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press";
		return RunScript.runWScript(code);
	}

	/**
	 * set maximum of showed data sets
	 * 
	 * @return scripting process
	 */
	public static Process setMaxOfHits(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/txtGD-MAX_LINES\").text = \"10000\" \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0";
		return RunScript.runWScript(code);
	}

	/**
	 * create a vbs script to edit every PARNR of a table
	 * 
	 * @param numberOfHits
	 *            is same as the number of PARNR to edit
	 * @param valuenew
	 *            save it to each PARNR field
	 * @return
	 */
	public static Process edit(int numberOfHits, String value, String valuenew,
			int connection, int window) {
		value = value.toUpperCase();
		String edit = WindowsManagement.start(connection, window);
		edit += "Dim numberOfHits \n"
				+ "numberOfHits = "
				+ numberOfHits
				+ " \n"
				+ "Dim oldValue \n"
				+ "oldValue = \""
				+ value
				+ "\" \n"
				+ "Dim newValue \n"
				+ "newValue = \""
				+ valuenew
				+ "\" \n"
				+ "For i = 0 To numberOfHits-1 \n"
				+ "session.findById(\"wnd[0]/usr/cntlRESULT_LIST/shellcont/shell\").firstVisibleRow = i \n"
				+ "If UCase(session.findById(\"wnd[0]/usr/cntlRESULT_LIST/shellcont/shell\").GetCellValue(i,\"PARNR\")) = UCase(oldValue) Then \n"
				+ "session.findById(\"wnd[0]/usr/cntlRESULT_LIST/shellcont/shell\").modifyCell i,\"PARNR\",newValue \n"
				+ "End If \n" + "Next \n";
		return RunScript.runWScript(edit);
	}

}

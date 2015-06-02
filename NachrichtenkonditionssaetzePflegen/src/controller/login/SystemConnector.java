package controller.login;

import java.io.IOException;

import controller.general.Processes;
import controller.general.RunScript;

public class SystemConnector implements Processes {
	private static String sapLogonPath = "C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\saplogon.exe";

	/**
	 * log in a SAP system
	 * @param system
	 * @param user
	 * @param pw
	 * @param mandant
	 */
	public static void login(String system, String user, String pw,
			String mandant) {
		String login = "On Error Resume Next \n"
				+ "If Not IsObject(application) Then \n"
				+ "Set SapGuiAuto  = GetObject(\"SAPGUI\")\n"
				+ "Set application = SapGuiAuto.GetScriptingEngine \n"
				+ "End If \n" + "If Not IsObject(connection) Then \n"
				+ "Set connection = application.OpenConnection(\""
				+ system
				+ "\") \n"
				+ "End If \n"
				+ "If Not IsObject(session) Then \n"
				+ "Set session    = connection.Children(0) \n"
				+ "End If \n"
				+ "If IsObject(WScript) Then \n"
				+ "WScript.ConnectObject session,     \"on\" \n"
				+ "WScript.ConnectObject application, \"on\" \n"
				+ "End If \n"
				+ "session.findById(\"wnd[0]/usr/txtRSYST-BNAME\").text =\""
				+ user
				+ "\"\n"
				+ "session.findById(\"wnd[0]/usr/pwdRSYST-BCODE\").text =\""
				+ pw
				+ "\"\n"
				+ "session.findById(\"wnd[0]/usr/txtRSYST-MANDT\").text =\""
				+ mandant
				+ "\"\n"
				+ "session.findById(\"wnd[0]\").sendVKey 0 \n"
				+ "session.findById(\"wnd[1]/usr/radMULTI_LOGON_OPT2\").select \n"
				+ "session.findById(\"wnd[1]/usr/radMULTI_LOGON_OPT2\").setFocus \n"
				+ "session.findById(\"wnd[1]/tbar[0]/btn[0]\").press \n";
		try {
			RunScript.runWScriptForApp(login).waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * open SAP Logon
	 */
	public static void connectSAPLogon() {
		try {
			app.add(new ProcessBuilder(sapLogonPath).start());
			Thread.sleep(2000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * close all processes
	 */
	public static void disconnectAllProcesses() {
		for (int i = 0; i < scripts.size(); i++) {
			// close all script processes
			scripts.get(i).destroy();
		}
		for (int i = 0; i < app.size(); i++) {
			// close all applications
			app.get(i).destroy();
		}
		
	}
}

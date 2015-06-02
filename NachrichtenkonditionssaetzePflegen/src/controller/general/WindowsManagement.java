package controller.general;

public class WindowsManagement {

	private static final int WindowHeight = 779;
	private static final int WindowWidth = 1012;

	public static Process setStandardSize(int connection, int window) {
		String setSize = start(connection, window)
				+ "session.findById(\"wnd[0]\").height = "+WindowHeight+" \n"
				+ "session.findById(\"wnd[0]\").width = "+WindowWidth+" \n";
		return RunScript.runWScript(setSize);
	}

	public static int countConnections() {
		int result;
		try {
			result = Integer
					.parseInt(RunScript.getSingleValue(RunScript.runCScript(RunScript
							.readScript("windowsManagement/countConnections.txt"))));
		} catch (NullPointerException e) {
			return 0;
		}
		return result;
	}

	public static int countWindows(int connection) {
		String code = "If Not IsObject(application) Then \n"
				+ " Set SapGuiAuto  = GetObject(\"SAPGUI\") \n"
				+ " Set application = SapGuiAuto.GetScriptingEngine \n"
				+ "End If \n" + "If Not IsObject(connection) Then \n"
				+ " Set connection = application.Children(" + connection
				+ ") \n" + "End If \n" + "If IsObject(WScript) Then \n"
				+ "   WScript.ConnectObject application, \"on\" \n"
				+ "End If \n" + "WScript.Echo connection.children().count() \n";
		return Integer.parseInt(RunScript.getSingleValue(RunScript
				.runCScript(code)));
	}

	public static String getSystemName(int connection) {
		String code = start(connection, 0)
				+ "WScript.Echo session.info.SystemName";
		Process p = RunScript.runCScript(code);
		return RunScript.getSingleValue(p);
	}

	public static String getTransaction(int connection, int window) {
		String code = start(connection, window)
				+ "WScript.Echo session.info.transaction";
		Process p = RunScript.runCScript(code);
		return RunScript.getSingleValue(p);
	}

	public static void Logout(int connection, int window) {
		String code = start(connection, window)
				+ RunScript.readScript("windowsManagement/logout.txt");
		try {
			RunScript.runWScript(code).waitFor();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}
	}

	public static String start(int connection, int window) {
		return "If Not IsObject(application) Then \n"
				+ "	Set SapGuiAuto  = GetObject(\"SAPGUI\") \n"
				+ "	Set application = SapGuiAuto.GetScriptingEngine \n"
				+ "End If \n" + "If Not IsObject(connection) Then \n"
				+ "  Set connection = application.Children(" + connection
				+ ") \n" + "End If \n" + "If Not IsObject(session) Then \n"
				+ "   Set session    = connection.Children(" + window + ") \n"
				+ "End If \n";
	}

	public static Process closeWindow(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]\").close";
		return RunScript.runWScript(code);
	}

	public static int findSystem(int systemNr) {
		int i;
		for (i = 0; i < countConnections(); i++) {
			int temp = Integer.parseInt(RunScript.cleanText(getSystemName(i)));
			if (temp == systemNr) {
				return i;
			}
		}
		return -1;
	}
}

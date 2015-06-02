package controller.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import controller.editPAR.EditPAR;
import view.general.OptionPane;

public class RunScript implements Processes {

	/**
	 * execute a Transaction in SAP GUI
	 * 
	 * @param transaction
	 * @param newWindow
	 *            "n" for no new window, "o" for new window
	 * @param window
	 * @return scripting process
	 */
	public static Process execTransaction(String transaction, String newWindow,
			int connection, int window) {
		String exeT = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[0]/okcd\").text = \"" + "/"
				+ newWindow + transaction + "\" \n"
				+ "session.findById(\"wnd[0]\").sendVKey 0 \n";
		return RunScript.runWScript(exeT);
	}

	/**
	 * run w-script with a string as code
	 * 
	 * @param code
	 * @return scripting process
	 */
	public static Process runWScript(String code) throws NullPointerException {
		Process p = null;
		File file;
		code = "On Error Resume Next \n" + code + " \n On Error Goto 0 \n";
		try {
			file = File.createTempFile("script", ".vbs");
			file.deleteOnExit();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(code);
			fileWriter.close();
			p = Runtime.getRuntime().exec("wscript " + file.getPath());
			// as script
			scripts.add(p);
		} catch (IOException e) {
			OptionPane.showMessage("IOException by run wscript");
			System.exit(1);
		}
		if (p == null) {
			throw new NullPointerException();
		}
		return p;
	}

	/**
	 * run w-script with a string as code
	 * 
	 * @param code
	 * @return application
	 */
	public static Process runWScriptForApp(String code)
			throws NullPointerException {
		Process p = null;
		File file;
		code = "On Error Resume Next \n" + code + " \n On Error Goto 0 \n";
		try {
			file = File.createTempFile("script", ".vbs");
			file.deleteOnExit();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(code);
			fileWriter.close();
			p = Runtime.getRuntime().exec("wscript " + file.getPath());
			// as application
			app.add(p);
		} catch (IOException e) {
			OptionPane.showMessage("IOException by run wscript");
			System.exit(1);
		}
		if (p == null) {
			throw new NullPointerException();
		}
		return p;
	}

	/**
	 * run c-script with a string as code
	 * 
	 * @param code
	 * @return scripting process
	 */
	public static Process runCScript(String code) throws NullPointerException {
		Process p = null;
		File file;
		code = "On Error Resume Next \n" + code + " \n On Error Goto 0 \n";
		try {
			file = File.createTempFile("script", ".vbs");
			file.deleteOnExit();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(code);
			fileWriter.close();
			p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			scripts.add(p);
		} catch (IOException e) {
			OptionPane.showMessage("IOException by run cscript");
			System.exit(1);
		}
		if (p == null) {
			throw new NullPointerException();
		}
		return p;
	}

	/**
	 * @param p
	 * @return single value from a scripting process
	 */
	public static String getSingleValue(Process p) throws NullPointerException {
		String result = "";
		InputStreamReader isr = new InputStreamReader(p.getInputStream());
		BufferedReader input = new BufferedReader(isr);
		try {
			result = input.readLine();
			p.getInputStream().close();
			isr.close();
			input.close();
		} catch (IOException e) {
			OptionPane.showMessage("IOException by read cscript");
			System.exit(1);
		}
		if (result == null)
			throw new NullPointerException();
		return result.trim();
	}

	/**
	 * @param p
	 * @param length
	 *            number of values
	 * @return all values from a scripting process
	 */
	public static String[] getValue(Process p) throws NullPointerException {
		ArrayList<String> value = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(p.getInputStream());
		BufferedReader input = new BufferedReader(isr);
		String line;
		try {
			while ((line = input.readLine()) != null) {
				value.add(line);
			}
			p.getInputStream().close();
			isr.close();
			input.close();
		} catch (IOException e) {
			OptionPane.showMessage("IOException by read cscript");
			System.exit(1);
		}
		return value.toArray(new String[value.size()]);
	}

	/**
	 * get code from script
	 * 
	 * @param path
	 * @return code as text
	 */
	public static String readScript(String path) {
		InputStream is = EditPAR.class.getClassLoader().getResourceAsStream(
				path);
		String script = "";
		// if file not found
		if (is == null) {
			OptionPane.showMessage("Resource not found.");
			System.exit(1);
		}
		String line;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		try {
			while ((line = reader.readLine()) != null) {
				script += line + "\n";
			}
			is.close();
			isr.close();
			reader.close();
		} catch (IOException e) {
			OptionPane.showMessage("IOException by read script");
			System.exit(1);
		}
		return script;
	}

	/**
	 * find out a number from text
	 * 
	 * @param original
	 *            a text with number
	 * @return only a number
	 */
	public static String cleanText(String original) {
		String result = "";
		char temp;
		for (int i = 0; i < original.length(); i++) {
			if ((temp = original.charAt(i)) >= '0' && temp <= '9') {
				result += temp;
			}
		}
		return result;
	}

	public static Process save(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[0]/btn[11]\").press";
		return RunScript.runWScript(code);
	}

	public static Process saveWithSubmit(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/tbar[0]/btn[11]\").press \n"
				+ "session.findById(\"wnd[1]/tbar[0]/btn[0]\").press";
		return RunScript.runWScript(code);
	}

	public static Process scrollDown(int index, int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY\")."
				+ "verticalScrollbar.position = " + index + " \n";
		return RunScript.runWScript(code);
	}

}

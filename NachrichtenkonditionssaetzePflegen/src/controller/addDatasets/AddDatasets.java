package controller.addDatasets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import view.general.OptionPane;
import controller.editPAR.EditPAR;
import controller.general.Processes;
import controller.general.RunScript;
import controller.general.WindowsManagement;

public class AddDatasets implements Processes {
	private final static int NumberOfShowedRows = 21;

	public static Process enterConditionType(String conditionType,
			int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtRV13B-KSCHL\").text = \""
				+ conditionType + "\" \n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[17]\").press \n";
		if (conditionType == "YCRM" || conditionType == "YPIA") {
			code += "session.findById(\"wnd[1]/usr/sub:SAPLV14A:0100/radRV130-SELKZ[2,0]\").select \n"
					+ "session.findById(\"wnd[1]/usr/sub:SAPLV14A:0100/radRV130-SELKZ[2,0]\").setFocus \n";
		}
		code += "session.findById(\"wnd[1]/tbar[0]/btn[0]\").press \n";
		return RunScript.runWScript(code);
	}

	public static Process enterPlant(String plant, int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+ "session.findById(\"wnd[0]/usr/ctxtF001\").text = \""
				+ plant
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/ctxtF001\").caretPosition = 4 \n"
				+ "session.findById(\"wnd[0]/tbar[1]/btn[8]\").press";
		return RunScript.runWScript(code);
	}

	public static Process readSingleDataset(int index, int connection,
			int window) {
		String code = WindowsManagement.start(connection, window)
				+ "Wscript.Echo session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtKOMB-ACTION[0,"
				+ index + "]\").text \n";
		return RunScript.runCScript(code);
	}

	public static String[] readAllDatasets(int connection, int window) {
		String code = WindowsManagement.start(connection, window)
				+"Dim lastRow \n"
				+ "lastRow = "+NumberOfShowedRows+" \n"
				+ RunScript.readScript("addDatasets/readActions.txt");
		return RunScript.getValue(RunScript.runCScript(code));
	}

	public static String[] readAllDatasetsFromFile(String fileName) {
		InputStream is = null;
		if (fileName.charAt(0) == 'C' && fileName.charAt(1) == ':') {
			// in file temp
			try {
				is = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				OptionPane.showMessage("Resource not found.");
				System.exit(1);
			}
		} else {
			// in jar
			is = EditPAR.class.getClassLoader().getResourceAsStream(fileName);
		}
		if (is == null) {
			OptionPane.showMessage("Resource not found.");
			System.exit(1);
		}
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String line;
		ArrayList<String> result = new ArrayList<String>();
		try {
			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
			is.close();
			isr.close();
			reader.close();
		} catch (IOException e) {
			OptionPane.showMessage("failed by read data file");
			System.exit(1);
		}
		return result.toArray(new String[result.size()]);
	}

	public static void fillPlant(String fileName, String par, int connection,
			int window) {
		String[] src = readAllDatasetsFromFile(fileName);
		String[] currentData = readAllDatasets(connection, window);
		int row;
		int scroll;
		if (currentData.length > getNumberOfShowedRows() - 1) {
			row = getNumberOfShowedRows() - 1;
			scroll = currentData.length - getNumberOfShowedRows() + 2;
		} else {
			row = currentData.length;
			scroll = 1;
		}
		for (int i = 0; i < src.length; i++) {
			if (existConditionRecs(currentData, src[i])) {
				continue;
			}
			try {
				fillOneAction(src[i], par, row, connection).waitFor();
				if (row == getNumberOfShowedRows() - 1) {
					// System.out.println(row+", "+scoll);
					RunScript.scrollDown(scroll, connection, window).waitFor();
					scroll++;
				} else {
					row++;
				}
			} catch (InterruptedException e) {
				OptionPane.showMessage("failed by fill data");
			}
		}
	}

	public static Process fillOneAction(String recs, String par, int row,
			int connection) {
		String code = WindowsManagement.start(connection, 0)
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtKOMB-ACTION[0,"
				+ row
				+ "]\").text = \""
				+ recs
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtRV13B-PARNR[3,"
				+ row
				+ "]\").text = \""
				+ par
				+ "\" \n"
				+ "session.findById(\"wnd[0]/usr/tblSAPMV13BTCTRL_FAST_ENTRY/ctxtNACH-SPRAS[6,"
				+ row + "]\").text = \"EN\" \n";
		return RunScript.runWScript(code);
	}

	/**
	 * extra method
	 * 
	 * @param fileName
	 * @return
	 */
	public static String writeAllDatasetsToFile(String fileName,
			int connection, int window) {
		String[] dataSets = readAllDatasets(connection, window);
		File file = null;
		try {
			file = File.createTempFile(fileName, ".txt");
			file.deleteOnExit();
			FileWriter writer = new FileWriter(file);
			for (int i = 0; i < dataSets.length; i++) {
				writer.write(dataSets[i]);
				if (i != dataSets.length - 1)
					writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			OptionPane.showMessage("failed by export data");
		}
		if (file == null) {
			OptionPane.showMessage("no file");
		}
		return file.getPath();
	}

	private static boolean existConditionRecs(String[] src, String c) {
		for (int i = 0; i < src.length; i++) {
			if (c.toUpperCase().equals(src[i].toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public static int getNumberOfShowedRows() {
		return NumberOfShowedRows;
	}
}

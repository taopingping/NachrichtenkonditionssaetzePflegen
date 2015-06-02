package controller.addDatasets;

import controller.general.RunScript;
import controller.general.WindowsManagement;

public class AddDatasetsEditor {

	private int connection;
	private int windowToEdit = 0;
	private int windowToReadData;

	public void setConnection(int i) {
		connection = i;
	}

	public void showTable(String type, String plant)
			throws InterruptedException {
		RunScript.execTransaction("velok", "n", connection, windowToEdit)
				.waitFor();
		WindowsManagement.setStandardSize(connection, windowToEdit).waitFor();
		AddDatasets.enterConditionType(type, connection, windowToEdit)
				.waitFor();
		AddDatasets.enterPlant(plant, connection, windowToEdit).waitFor();
	}

	public String getTable2(String type, String plant)
			throws InterruptedException {
		windowToReadData = WindowsManagement.countWindows(connection);
		RunScript.execTransaction("velok", "o", connection, windowToEdit)
				.waitFor();
		WindowsManagement.setStandardSize(connection, windowToReadData).waitFor();
		AddDatasets.enterConditionType(type, connection, windowToReadData)
				.waitFor();
		AddDatasets.enterPlant(plant, connection, windowToReadData).waitFor();
		String path = AddDatasets.writeAllDatasetsToFile(type, connection,
				windowToReadData);
		WindowsManagement.closeWindow(connection, windowToReadData).waitFor();
		return path;
	}

	public void fillOnePlant(String fileName, String par, String plant)
			throws InterruptedException {
		if (plant.length() == 0) {
			AddDatasets.fillPlant("actions/" + fileName + ".txt", par,
					connection, windowToEdit);
		} else {
			AddDatasets.fillPlant(getTable2(fileName, plant), par,
					connection, windowToEdit);
		}
	}

	public void fillData(String kschl, String vakey, String par,
			String patternPlant) {
		try {
			showTable(kschl, vakey);
			Thread.sleep(500);
			fillOnePlant(kschl, par, patternPlant);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void save() {
		try {
			RunScript.save(connection, windowToEdit).waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

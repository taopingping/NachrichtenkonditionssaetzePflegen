package controller.addDatasets;

import controller.general.RunScript;
import controller.general.WindowsManagement;

public class AddDatasets06Editor {

	private int connection;
	private int windowToEdit;
	private int windowToRead;
	private boolean stop;

	public void setConnection(int i) {
		connection = i;
	}
	
	public void stop(){
		stop = true;
	}

	public void fill(String kschl, String importer, String plant,
			String parNr) throws InterruptedException {
		windowToEdit = WindowsManagement.countWindows(connection);
		RunScript.execTransaction("velok", "o", connection, 0).waitFor();
		WindowsManagement.setStandardSize(connection, windowToEdit).waitFor();
		AddDatasets06.insertConditionType(kschl, connection, windowToEdit)
				.waitFor();
		windowToRead = WindowsManagement.countWindows(connection);
		RunScript.execTransaction("ytab", "o", connection, 0).waitFor();
		WindowsManagement.setStandardSize(connection, windowToRead).waitFor();
		AddDatasets06.tableNach(connection, windowToRead).waitFor();
		AddDatasets06.setListWidth(connection, windowToRead).waitFor();
		AddDatasets06.fillVariableToNach(plant, kschl, connection,
				windowToRead).waitFor();
		int numberOfRows = Integer.parseInt(RunScript
				.cleanText(AddDatasets06.getNumberOfRows(connection,
						windowToRead)));
		String[] actions = AddDatasets06.readActions(numberOfRows,
				connection, windowToRead);
		WindowsManagement.closeWindow(connection, windowToRead).waitFor();
		for (int i = 0; i < actions.length&&!stop; i++) {
			AddDatasets06.insertAction(actions[i], connection,
					windowToEdit).waitFor();
			System.out.println((i + 1) + ": " + actions[i]);
			if (AddDatasets06.fillDataset(importer, parNr, connection,
					windowToEdit)) {
				RunScript.save(connection, windowToEdit).waitFor();
//				AddDatasets06.back(connection, windowToEdit).waitFor();
			} else {
				AddDatasets06.back(connection, windowToEdit).waitFor();
			}
		}
		WindowsManagement.closeWindow(connection, windowToEdit).waitFor();
	}

	public void reset() {
		stop = false;
		
	}
}

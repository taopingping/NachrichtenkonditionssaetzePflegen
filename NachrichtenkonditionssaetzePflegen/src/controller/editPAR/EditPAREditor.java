package controller.editPAR;

import controller.general.RunScript;
import controller.general.WindowsManagement;
import view.general.OptionPane;

public class EditPAREditor {

	private  int numberOfDatasets;
	private  int connection;
	private  int windowToEdit = 0;
	private  int debugWindow;

	public void setConnection(int i) {
		connection = i;
	}

	/**
	 * show the table with certain kschl and vakey in debugging modus
	 * 
	 * @param kschl
	 * @param vakey
	 * @throws InterruptedException
	 */
	public void showTable(String kschl, String vakey)
			throws InterruptedException {
		RunScript.execTransaction("se16n", "n", connection, windowToEdit)
				.waitFor();
		WindowsManagement.setStandardSize(connection, windowToEdit).waitFor();
		EditPAR.insertTableNACH(connection, windowToEdit).waitFor();
		EditPAR.debugModus(connection, windowToEdit);
		debugWindow = WindowsManagement.countWindows(connection);
		Thread.sleep(2000);
		// window was changed to debugging session
		EditPAR.fillVariables(connection, debugWindow);
		Thread.sleep(2000);
		// window was changed back
		EditPAR.setFilter(kschl, vakey, connection, windowToEdit)
				.waitFor();
		EditPAR.setMaxOfHits(connection, windowToEdit).waitFor();
		EditPAR.showTable(connection, windowToEdit).waitFor();
	}

	/**
	 * edit all PARNR with same kschl and vakey values and save it
	 * 
	 * @param numberOfHits
	 *            if it is "0", there is no field to edit
	 * @param value
	 *            the value to replace
	 */
	public void edit(String value, String valueNew) {
		try {
			numberOfDatasets = Integer.parseInt(RunScript.cleanText(EditPAR
					.getNumberOfHits(connection, windowToEdit)));
			EditPAR
					.edit(numberOfDatasets, value, valueNew, connection, windowToEdit)
					.waitFor();
		} catch (NullPointerException n) {
			OptionPane.showMessage("Keine editierbare Tabelle");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			RunScript.saveWithSubmit(connection, windowToEdit).waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

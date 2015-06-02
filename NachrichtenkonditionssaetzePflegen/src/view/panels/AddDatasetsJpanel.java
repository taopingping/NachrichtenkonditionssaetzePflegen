package view.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.general.ElementSetting;
import view.general.OptionPane;
import view.general.PerformEvents;
import view.general.UserWindow;
import controller.addDatasets.AddDatasetsEditor;
import controller.general.WindowsManagement;

@SuppressWarnings("serial")
public class AddDatasetsJpanel extends JPanel implements ElementSetting {
	protected AddDatasetsEditor editor = new AddDatasetsEditor();
	protected int systemNr;
	
	protected GridBagLayout gblayout = new GridBagLayout();
	protected GridBagConstraints constrains = new GridBagConstraints();

	protected JLabel kschlLabel = new JLabel("KSCHL");
	protected JLabel vakeyLabel = new JLabel("VAKEY");
	protected JLabel parLabel = new JLabel("PARNR");
	protected JComboBox<String> kschl = new JComboBox<String>(kschlPVMS);
	protected JLabel patternPlantLabel = new JLabel("Musterwerk");
	protected JTextField patternPlant = new JTextField();
	protected JTextField vakey = new JTextField();
	protected JTextField parNr = new JTextField();
	protected JButton submitButton = new JButton("Bestätigen");
	protected JButton showTableButton = new JButton("Tabelle anzeigen");
	protected JButton saveButton = new JButton("Änderung Speichern");

	public AddDatasetsJpanel(int systemNr) {
		this.systemNr = systemNr;
		constrains.anchor = GridBagConstraints.EAST;
		constrains.insets = new Insets(10, 20, 10, 20);
		this.setLayout(gblayout);
		constrains.gridx = 0;
		constrains.gridy = 0;
		gblayout.setConstraints(kschlLabel, constrains);
		this.add(kschlLabel);
		constrains.gridx = 1;
		constrains.gridy = 0;
		gblayout.setConstraints(kschl, constrains);
		this.add(kschl);
		constrains.gridx = 0;
		constrains.gridy = 1;
		gblayout.setConstraints(vakeyLabel, constrains);
		this.add(vakeyLabel);
		constrains.gridx = 1;
		constrains.gridy = 1;
		gblayout.setConstraints(vakey, constrains);
		this.add(vakey);
		constrains.gridx = 0;
		constrains.gridy = 2;
		constrains.gridwidth = 2;
		constrains.anchor = GridBagConstraints.CENTER;
		gblayout.setConstraints(showTableButton, constrains);
		this.add(showTableButton);
		constrains.gridx = 0;
		constrains.gridy = 3;
		constrains.gridwidth = 1;
		constrains.anchor = GridBagConstraints.EAST;
		gblayout.setConstraints(patternPlantLabel, constrains);
		add(patternPlantLabel);
		constrains.gridx = 1;
		constrains.gridy = 3;
		gblayout.setConstraints(patternPlant, constrains);
		add(patternPlant);
		constrains.gridx = 0;
		constrains.gridy = 4;
		gblayout.setConstraints(parLabel, constrains);
		this.add(parLabel);
		constrains.gridx = 1;
		constrains.gridy = 4;
		gblayout.setConstraints(parNr, constrains);
		this.add(parNr);
		constrains.gridx = 0;
		constrains.gridy = 5;
		constrains.gridwidth = 2;
		constrains.anchor = GridBagConstraints.CENTER;
		gblayout.setConstraints(submitButton, constrains);
		this.add(submitButton);
		constrains.gridx = 0;
		constrains.gridy = 6;
		gblayout.setConstraints(saveButton, constrains);
		this.add(saveButton);

		kschl.setPreferredSize(inputDimension);
		vakey.setPreferredSize(inputDimension);
		showTableButton.setPreferredSize(buttonDimension);
		parNr.setPreferredSize(inputDimension);
		submitButton.setPreferredSize(buttonDimension);
		saveButton.setPreferredSize(buttonDimension);
		patternPlant.setPreferredSize(inputDimension);

		PerformEvents.cleanTextOnclick(patternPlant);
		PerformEvents.cleanTextOnclick(parNr);
		PerformEvents.cleanTextOnclick(vakey);
		showTable();
		submit();
		save();
		testConnection(saveButton);
		testConnection(showTableButton);
		testConnection(submitButton);
	}

	private void showTable() {
		showTableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (kschl.getSelectedItem().toString().equals("")) {
					OptionPane
							.showMessage("Wählen Sie bitte einen Konditionstyp aus!");
				} else if (vakey.getText().equals("")) {
					OptionPane
							.showMessage("Geben Sie bitte ein gültigen Werk ein!");
				} else if (WindowsManagement.countConnections() == 0) {
					OptionPane.showMessage("Keine Application getartet");
				} else {
					try {
						editor.showTable(kschl.getSelectedItem()
								.toString(), vakey.getText().toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void submit() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (kschl.getSelectedItem().toString().equals("")) {
					OptionPane
							.showMessage("Wählen Sie bitte einen Konditionstyp aus!");
				} else if (vakey.getText().equals("")) {
					OptionPane
							.showMessage("Geben Sie bitte ein gültigen Werk ein!");
				} else if (patternPlant.getText().equals(vakey.getText())) {
					OptionPane
							.showMessage("Das Muster sollte nicht gleich sein wie das Werk!");
				} else if (WindowsManagement.countConnections() == 0) {
					OptionPane.showMessage("Keine Application getartet");
				} else {
					editor.fillData(kschl.getSelectedItem().toString(),
							vakey.getText(), parNr.getText(),
							patternPlant.getText());
				}
			}
		});
	}

	private void save() {
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (WindowsManagement.countConnections() == 0) {
					OptionPane.showMessage("Keine Application getartet");
				} else {
					editor.save();
				}
			}
		});
	}
	
	private void testConnection(JButton b){
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int connection = WindowsManagement.findSystem(systemNr);
				if(connection == -1){
					OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
					UserWindow.toLogon();
				}else{
					editor.setConnection(connection);
				}
			}
		});
	}
	
	public void setConnection(int i){
		editor.setConnection(i);
	}

}

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
import controller.addDatasets.AddDatasets06Editor;
import controller.general.WindowsManagement;

@SuppressWarnings("serial")
public class AddDatasets06Jpanel extends JPanel implements ElementSetting {
	protected AddDatasets06Editor editor = new AddDatasets06Editor();

	protected GridBagLayout gblayout = new GridBagLayout();
	protected GridBagConstraints constrains = new GridBagConstraints();

	protected JLabel kschlLabel = new JLabel("KSCHL");
	protected JLabel importerLabel = new JLabel("Importeur");
	protected JLabel parNrLabel = new JLabel("PARNR");
	protected JComboBox<String> kschl;
	protected JLabel importerPatternLabel = new JLabel("Musterimport.");
	protected JTextField patternImporter = new JTextField("");
	protected JTextField importer = new JTextField("");
	protected JTextField parNr = new JTextField("");
	protected JButton submitButton = new JButton("Bestätigen");
	protected JButton stopButton = new JButton("Abbrechen");

	public AddDatasets06Jpanel() {
		kschl = new JComboBox<String>(kschl06);
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
		gblayout.setConstraints(importerLabel, constrains);
		this.add(importerLabel);
		constrains.gridx = 1;
		constrains.gridy = 1;
		gblayout.setConstraints(importer, constrains);
		this.add(importer);
		constrains.gridx = 0;
		constrains.gridy = 3;
		constrains.gridwidth = 1;
		constrains.anchor = GridBagConstraints.EAST;
		gblayout.setConstraints(importerPatternLabel, constrains);
		add(importerPatternLabel);
		constrains.gridx = 1;
		constrains.gridy = 3;
		gblayout.setConstraints(patternImporter, constrains);
		add(patternImporter);
		constrains.gridx = 0;
		constrains.gridy = 4;
		gblayout.setConstraints(parNrLabel, constrains);
		this.add(parNrLabel);
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
		gblayout.setConstraints(stopButton, constrains);
		add(stopButton);

		kschl.setPreferredSize(inputDimension);
		importer.setPreferredSize(inputDimension);
		parNr.setPreferredSize(inputDimension);
		submitButton.setPreferredSize(buttonDimension);
		stopButton.setPreferredSize(buttonDimension);
		patternImporter.setPreferredSize(inputDimension);
		PerformEvents.cleanTextOnclick(patternImporter);
		PerformEvents.cleanTextOnclick(importer);
		PerformEvents.cleanTextOnclick(parNr);
		submit();
		stop();
		testConnection(submitButton);

	}

	private void submit() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (kschl.getSelectedItem().toString().equals("")) {
					OptionPane
							.showMessage("Wählen Sie bitte einen Konditionstyp aus!");
				} else if (importer.getText().equals("")) {
					OptionPane
							.showMessage("Geben Sie bitte ein gültigen Werk ein!");
				} else if (patternImporter.getText().equals(importer.getText())) {
					OptionPane
							.showMessage("Das Muster sollte nicht gleich sein wie das Werk!");
				} else if (WindowsManagement.countConnections() == 0) {
					OptionPane.showMessage("Keine Application getartet");
				} else {
					new Thread() {
						public void run() {
							try {
								editor.fill(kschl.getSelectedItem()
										.toString(), importer.getText(),
										patternImporter.getText(), parNr
												.getText());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}.start();
				}
			}
		});

	}
	
	private void stop(){
		stopButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.stop();	
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.reset();
			}
		});
	}

	private void testConnection(JButton b) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int connection = WindowsManagement.findSystem(6);
				if (connection == -1) {
					OptionPane
							.showMessage("Melden Sie sich bitte am Zentralsystem an.");
					UserWindow.toLogon();
				} else {
					editor.setConnection(connection);
				}
			}
		});
	}
	
	public void setConnection(int i){
		editor.setConnection(i);
	}
}

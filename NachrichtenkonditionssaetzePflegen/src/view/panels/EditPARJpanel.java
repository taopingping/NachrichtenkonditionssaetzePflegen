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
import controller.editPAR.EditPAREditor;
import controller.general.WindowsManagement;

@SuppressWarnings("serial")
public class EditPARJpanel extends JPanel implements ElementSetting {
	protected int systemNr;
	protected EditPAREditor editor = new EditPAREditor();
	
	protected GridBagLayout gblayout = new GridBagLayout();
	protected GridBagConstraints constrains = new GridBagConstraints();

	protected JLabel kschlLabel = new JLabel("KSCHL");
	protected JLabel vakeyLabel = new JLabel("VAKEY");
	protected JLabel parLabel = new JLabel("IST-PARNR");
	protected JLabel parNewLabel = new JLabel("SOLL-PARNR");
	protected JComboBox<String> kschl;
	protected JTextField vakey = new JTextField("");
	protected JTextField parValue = new JTextField("");
	protected JTextField parNewValue = new JTextField("");
	protected JButton submitButton = new JButton("Bestätigen");
	protected JButton showTableButton = new JButton(
			"Tabelle in Debugging-Modus anzeigen");
	protected JButton saveButton = new JButton("Änderung Speichern");

	public EditPARJpanel(int system) {
		this.systemNr = system;
		if(systemNr == 6){
			kschl = new JComboBox<String>(kschl06);
		}else{
			kschl = new JComboBox<String>(kschlPVMS);
		}
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
		constrains.gridy = 4;
		constrains.gridwidth = 1;
		constrains.anchor = GridBagConstraints.EAST;
		gblayout.setConstraints(parLabel, constrains);
		this.add(parLabel);
		constrains.gridx = 1;
		constrains.gridy = 4;
		gblayout.setConstraints(parValue, constrains);
		this.add(parValue);
		constrains.gridx = 0;
		constrains.gridy = 5;
		constrains.gridwidth = 1;
		constrains.anchor = GridBagConstraints.EAST;
		gblayout.setConstraints(parNewLabel, constrains);
		this.add(parNewLabel);
		constrains.gridx = 1;
		constrains.gridy = 5;
		gblayout.setConstraints(parNewValue, constrains);
		this.add(parNewValue);
		constrains.gridx = 0;
		constrains.gridy = 6;
		constrains.gridwidth = 2;
		constrains.anchor = GridBagConstraints.CENTER;
		gblayout.setConstraints(submitButton, constrains);
		this.add(submitButton);
		constrains.gridx = 0;
		constrains.gridy = 7;
		gblayout.setConstraints(saveButton, constrains);
		this.add(saveButton);

		kschl.setPreferredSize(inputDimension);
		vakey.setPreferredSize(inputDimension);
		parValue.setPreferredSize(inputDimension);
		parNewValue.setPreferredSize(inputDimension);
		showTableButton.setPreferredSize(buttonDimension);
		submitButton.setPreferredSize(buttonDimension);
		saveButton.setPreferredSize(buttonDimension);

		PerformEvents.cleanTextOnclick(vakey);
		PerformEvents.cleanTextOnclick(parValue);
		PerformEvents.cleanTextOnclick(parNewValue);
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
				if (WindowsManagement.countConnections() == 0) {
					OptionPane.showMessage("Keine Application getartet");
				} else {
					try {
						editor.showTable(kschl.getSelectedItem().toString(),
								vakey.getText());
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
				new Thread() {
					public void run() {
						if (WindowsManagement.countConnections() == 0) {
							OptionPane
									.showMessage("Keine Application getartet");
						} else {
							editor.edit(parValue.getText(),
									parNewValue.getText());
						}
					}
				}.start();
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

	private void testConnection(JButton b) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int connection = WindowsManagement.findSystem(systemNr);
				if (connection == -1) {
					OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
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

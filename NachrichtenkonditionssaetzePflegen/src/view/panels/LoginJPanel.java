package view.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import view.general.ElementSetting;
import view.general.Verification;
import view.general.OptionPane;
import view.general.PerformEvents;
import controller.login.SystemConnector;
import controller.login.SystemNameException;
import controller.login.SystemNames;

@SuppressWarnings("serial")
public class LoginJPanel extends JPanel implements ElementSetting {

	private JLabel userNameLabel = new JLabel("Benutzername");
	private JLabel passwortLabel = new JLabel("Passwort");
	private JLabel systemLabel = new JLabel("System");
	private JLabel mandantLabel = new JLabel("Mandant");
	private JTextField userName = new JTextField("");
	private JPasswordField passwort = new JPasswordField("");
	private JTextField systemName = new JTextField("");
	private JTextField mandant = new JTextField("");
	private JButton loginButton = new JButton("Anmelden");

	public LoginJPanel() {
		GridBagLayout gblayout = new GridBagLayout();
		GridBagConstraints constrains = new GridBagConstraints();
		// loginJPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1,
		// 1));
		this.setLayout(gblayout);
		constrains.anchor = GridBagConstraints.EAST;
		constrains.insets = new Insets(10, 20, 10, 20);
		constrains.gridx = 0;
		constrains.gridy = 0;
		gblayout.setConstraints(userNameLabel, constrains);
		this.add(userNameLabel);
		constrains.gridx = 1;
		constrains.gridy = 0;
		gblayout.setConstraints(userName, constrains);
		this.add(userName);
		constrains.gridx = 0;
		constrains.gridy = 1;
		gblayout.setConstraints(passwortLabel, constrains);
		this.add(passwortLabel);
		constrains.gridx = 1;
		constrains.gridy = 1;
		gblayout.setConstraints(passwort, constrains);
		this.add(passwort);
		constrains.gridx = 0;
		constrains.gridy = 2;
		gblayout.setConstraints(systemLabel, constrains);
		this.add(systemLabel);
		constrains.gridx = 1;
		constrains.gridy = 2;
		gblayout.setConstraints(systemName, constrains);
		this.add(systemName);
		constrains.gridx = 0;
		constrains.gridy = 3;
		gblayout.setConstraints(mandantLabel, constrains);
		this.add(mandantLabel);
		constrains.gridx = 1;
		constrains.gridy = 3;
		gblayout.setConstraints(mandant, constrains);
		this.add(mandant);
		constrains.gridx = 0;
		constrains.gridy = 4;
		constrains.anchor = GridBagConstraints.CENTER;
		constrains.gridwidth = 2;
		gblayout.setConstraints(loginButton, constrains);
		this.add(loginButton);

		// set elements size
		userName.setPreferredSize(inputDimension);
		passwort.setPreferredSize(inputDimension);
		systemName.setPreferredSize(inputDimension);
		mandant.setPreferredSize(inputDimension);
		loginButton.setPreferredSize(buttonDimension);

		PerformEvents.cleanTextOnclick(userName);
		PerformEvents.cleanTextOnclick(passwort);
		PerformEvents.cleanTextOnclick(systemName);
		PerformEvents.cleanTextOnclick(mandant);
		setListener();
	}

	private void setListener() {

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
				// clear password field
				passwort.setText("");
			}
		});
	}

	private void start() {
		if (!Verification.verifyUserName(userName.getText())) {
			// user name verification failed
			OptionPane.showMessage("Geben Sie bitte den Benutzername ein!");
		} else if (!Verification.verifyPasswort(passwort.getPassword())) {
			// password verification failed
			OptionPane.showMessage("Geben Sie bitte das Passwort ein!");
		} else if (!Verification.verifyMandant(mandant.getText())) {
			// mandant verification failed
			OptionPane.showMessage("Ungültiger Mandant!");
		} else {
			// open SAP Logon
			SystemConnector.connectSAPLogon();
			try {
				// login
				SystemConnector.login(
						SystemNames.getSystemName(systemName.getText()),
						userName.getText(),
						new String(new String(passwort.getPassword())),
						mandant.getText());
			} catch (SystemNameException e) {
				// system name verification failed
				OptionPane.showMessage("Ungültiger Systemname!");
			}
		}
	}

	public void setSystem(String system) throws SystemNameException {
		systemName.setText(SystemNames.getSystemName(system));
		
	}
}

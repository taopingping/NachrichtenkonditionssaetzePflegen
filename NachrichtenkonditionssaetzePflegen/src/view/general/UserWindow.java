package view.general;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.general.WindowsManagement;
import controller.login.SystemNameException;
import view.panels.AddDatasets06Jpanel;
import view.panels.AddDatasetsJpanel;
import view.panels.EditPARJpanel;
import view.panels.LoginJPanel;

@SuppressWarnings("serial")
public class UserWindow extends JFrame implements ElementSetting {

	private static JTabbedPane tabpane;
	private static LoginJPanel logon = new LoginJPanel();
	private static AddDatasets06Jpanel addDatasets06 = new AddDatasets06Jpanel();
	private static EditPARJpanel editPAR06 = new EditPARJpanel(6);	
	private static AddDatasetsJpanel addDatasets16 = new AddDatasetsJpanel(16);
	private static EditPARJpanel editPAR16 = new EditPARJpanel(16);
	private static AddDatasetsJpanel addDatasets17 = new AddDatasetsJpanel(17);
	private static EditPARJpanel editPAR17 = new EditPARJpanel(17);
	private static AddDatasetsJpanel addDatasets18 = new AddDatasetsJpanel(18);
	private static EditPARJpanel editPAR18 = new EditPARJpanel(18);
	
	private static int k06 = 6;
	private static int k16 = 16;
	private static int k17 = 17;
	private static int k18 = 18;


	public UserWindow() throws HeadlessException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/img/icon.png")));
		setTitle("Nachrichtenkonditionssätze pflegen");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dimension = this.getToolkit().getScreenSize();
		setLocation(((dimension.width) - this.getWidth()) / 2 + 200,
				(dimension.height - this.getHeight()) / 2);
		// getRootPane().setDefaultButton(loginButton);

		// JTabbedPane
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/img/slideIcon.png")));
		tabpane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabpane.setBackground(Color.WHITE);
		tabpane.addTab("Logon", icon,logon);
		tabpane.addTab("Neue Datensätze (Zentralsystem)", icon, addDatasets06);
		tabpane.addTab("Partnersystem ändern (Zentralsystem)", icon, editPAR06);
		tabpane.addTab("Neue Datensätze (K/P16)", icon, addDatasets16);
		tabpane.addTab("Partnersystem ändern (K/P16)", icon, editPAR16);
		tabpane.addTab("Neue Datensätze (K/P17)", icon, addDatasets17);
		tabpane.addTab("Partnersystem ändern (K/P17)", icon, editPAR17);
		tabpane.addTab("Neue Datensätze (K/P18)", icon, addDatasets18);
		tabpane.addTab("Partnersystem ändern (K/P18)", icon, editPAR18);
		tabpane.setBackgroundAt(0, Color.white);

		add(tabpane);
		pack();
		setResizable(false);
		//389, 631
		setListener();
	}

	private void setListener() {
		// set exit action by exit icon
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				PerformEvents.exit();
			}
		});

		tabpane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				int i = ((JTabbedPane) ce.getSource()).getSelectedIndex();
				int connection;
				switch(i){
				case 1:
					connection = WindowsManagement.findSystem(k06);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte am Zentralsystem an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k06);
					}else{
						addDatasets06.setConnection(connection);
					}
					break;
				case 2:
					connection = WindowsManagement.findSystem(k06);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte am Zentralsystem an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k06);
					}else{
						addDatasets06.setConnection(connection);
					}
					break;
				case 3:
					connection = WindowsManagement.findSystem(k16);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k16);
					}else{
						addDatasets16.setConnection(connection);
					}
					break;
				case 4:
					connection = WindowsManagement.findSystem(k16);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k16);
					}else{
						addDatasets16.setConnection(connection);
					}
					break;
				case 5:
					connection = WindowsManagement.findSystem(k17);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k17);
					}else{
						addDatasets17.setConnection(connection);
					}
					break;
				case 6:
					connection = WindowsManagement.findSystem(k17);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k17);
					}else{
						addDatasets17.setConnection(connection);
					}
					break;
				case 7:
					connection = WindowsManagement.findSystem(k18);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k18);
					}else{
						addDatasets18.setConnection(connection);
					}
					break;
				case 8:
					connection = WindowsManagement.findSystem(k18);
					if(connection == -1){
						OptionPane.showMessage("Melden Sie sich bitte in einem entprechenden System an.");
						tabpane.setSelectedIndex(0);
						setSystemField(k18);
					}else{
						addDatasets18.setConnection(connection);
					}
					break;
				}				
			}
		});
	}
	
	private void setSystemField(int system){
		try {
			logon.setSystem(""+system);
		} catch (SystemNameException e) {
			OptionPane.showMessage("Ungültiges System: "+ system);
		}
	}

	public static void toLogon() {
		tabpane.setSelectedIndex(0);
	}
}

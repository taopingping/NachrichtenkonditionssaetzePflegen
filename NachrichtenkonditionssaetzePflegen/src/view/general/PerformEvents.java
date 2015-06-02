package view.general;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import controller.login.SystemConnector;

public class PerformEvents {
	public static void cleanTextOnclick(final JTextField field) {
		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				field.setText("");
			}
		});
	}
	
	public static void exit() {
		SystemConnector.disconnectAllProcesses();
		System.exit(1);
	}
}

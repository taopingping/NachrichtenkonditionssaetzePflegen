package view.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OptionPane extends JDialog implements ActionListener, ElementSetting{
	
	public static void showMessage(String message){
        JDialog meinJDialog = new OptionPane(message);
        meinJDialog.setModal(true);
        meinJDialog.setVisible(true);
	}

	private JLabel messageLabel = new JLabel();
	private JButton okButton = new JButton("OK");
	private JPanel messagePanel = new JPanel();
	private JPanel okButtonPanel = new JPanel();
	
	public OptionPane(String message){
		this.setTitle("Message");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/img/icon.png")));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		messageLabel.setText(message);
		getRootPane().setDefaultButton(okButton);
		
		okButton.setPreferredSize(new Dimension(100,28));
		setLayout(new BorderLayout());
		GridBagLayout gbLayout = new GridBagLayout();
		messagePanel.setLayout(gbLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		gbLayout.setConstraints(messageLabel, c);
		messagePanel.add(messageLabel);
		okButtonPanel.add(okButton);
		add(messagePanel,BorderLayout.CENTER);
		add(okButtonPanel,BorderLayout.SOUTH);
		
		okButton.addActionListener(this);
		this.pack();
		if(this.getWidth()<300){
			this.setSize(300,120);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//close Dialog
		this.dispose();
	}
}

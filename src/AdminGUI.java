import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class AdminGUI extends JFrame {
	private final int WIDTH = 200;
	private final int HEIGHT = 110;

	public static void main(String[] args) {
		new AdminGUI();
	}

	AdminIDPanel adminIDPanel;
	JPanel buttonPanel;
	JPanel errorPanel;

	public AdminGUI() {

		setTitle("Orion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		adminIDPanel = new AdminIDPanel();
		buildButtonPanel();

		setLayout(new BorderLayout());

		add(adminIDPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void buildButtonPanel() {
		buttonPanel = new JPanel();

		JButton enterButton = new JButton("Enter");

		enterButton.addActionListener(new EnterButtonListener());

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener());

		buttonPanel.add(enterButton);
		buttonPanel.add(exitButton);
	}

	private class EnterButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				int admin_id = adminIDPanel.getadminID();
				if (DatabaseImporter.isAdminID(admin_id)) {
					// calls classPanel
					new ClassesGUI(admin_id).setVisible(true);
					
				} 
				else {

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	private class ExitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}

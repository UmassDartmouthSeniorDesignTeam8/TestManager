import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*entry system*/

public class ExamGUI extends JFrame {
	private final int WIDTH = 200;
	private final int HEIGHT = 110;
	
	public static void main(String[] args) {
		new ExamGUI();
	}

	examChoosenPanel examChoosenPanel;
	JPanel buttonPanel;

	public ExamGUI() {

		setTitle("Orion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		examChoosenPanel = new examChoosenPanel();
		buildButtonPanel();

		setLayout(new BorderLayout());		
		
		add(examChoosenPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error setting the look and feel.");
					System.exit(0);
				}


		pack();
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	public void buildButtonPanel() {
		buttonPanel = new JPanel();

		JButton submitButton = new JButton("Submit");

		submitButton.addActionListener(new SubmitButtonListener());

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener());

		buttonPanel.add(submitButton);
		buttonPanel.add(exitButton);
	}

	private class SubmitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				int exam_id = examChoosenPanel.getExamNumber();
				DatabaseImporter dbImport = new DatabaseImporter();
				dbImport.getQuestions(exam_id);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			//System.exit(0);
		}

	}

	private class ExitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}

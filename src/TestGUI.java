import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class TestGUI extends JFrame {
	private JButton select = new JButton("Select");

	public TestGUI(String class_name) throws SQLException{
		
		setLayout(new BorderLayout());		
		
		ArrayList<String> tests = DatabaseImporter.getTestNames(class_name);
		
		// create a combo box with items specified in the String array
		final JComboBox<String> availableTests = new JComboBox<String>(tests.toArray(new String[0]));
		
		
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				String testPicked = (String) availableTests.getSelectedItem();
				int test = 0;
				try {
					test = DatabaseImporter.getTestID(testPicked);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					//gets questions for a test. 
					DatabaseImporter.getQuestions(test);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
				
		add(availableTests, BorderLayout.NORTH);
		add(select, BorderLayout.SOUTH);
		pack();
	}

}

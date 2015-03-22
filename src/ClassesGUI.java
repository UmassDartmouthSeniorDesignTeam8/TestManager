import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;


public class ClassesGUI extends JFrame{
	private JButton select = new JButton("Select");
	
	public ClassesGUI(int admin_id) throws Exception {
		
		setLayout(new BorderLayout());		
		
		ArrayList<String> classes = DatabaseImporter.getClasses(admin_id);
		
		// create a combo box with items specified in the String array
		final JComboBox<String> availableClasses = new JComboBox<String>(classes.toArray(new String[0]));
		
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				String classPicked = (String) availableClasses.getSelectedItem();
				try {
					new TestGUI(classPicked).setVisible(true);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			}
		});
		
		add(availableClasses, BorderLayout.NORTH);
		add(select, BorderLayout.SOUTH);
		pack();		
	}
	
	
	
}

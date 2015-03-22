import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AdminIDPanel extends JPanel {

	private JTextField adminIDTextField;

	public AdminIDPanel(){
		
		JLabel adminIDPrompt = new JLabel("adminID: ");
		
		adminIDTextField = new JTextField(10);

		setLayout(new GridLayout(2,1));
		
		add(adminIDPrompt);
		add(adminIDTextField);
	}
	
	public int getadminID(){
		//String val = examNumberTextField.getText().trim();
		//if (val.length() > 0) {
		  return Integer.parseInt(adminIDTextField.getText().trim());
	   // }
	   // return -1;
	}
	
	public void clear(){
		adminIDTextField.setText("");
	}
	
}

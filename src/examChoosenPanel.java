import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*for the sample GUI*/

public class examChoosenPanel extends JPanel {

	private JTextField examNumberTextField;

	public examChoosenPanel(){
		
		JLabel examNumberPrompt = new JLabel("Exam: ");
		
		examNumberTextField = new JTextField(10);

		setLayout(new GridLayout(2,1));
		
		add(examNumberPrompt);
		add(examNumberTextField);
	}
	
	public int getExamNumber(){
		//String val = examNumberTextField.getText().trim();
		//if (val.length() > 0) {
		  return Integer.parseInt(examNumberTextField.getText().trim());
	   // }
	   // return -1;
	}
	
	public void clear(){
		examNumberTextField.setText("");
	}

}

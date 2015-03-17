import javax.swing.JFileChooser;
import javax.swing.JPanel;


public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Exam e = ExamGenerator.getExam();
		HTMLGenerator gen = new HTMLGenerator(e);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION){
			System.out.println(chooser.getCurrentDirectory().toString());
			System.out.println(chooser.getSelectedFile());
			gen.generateHTML(chooser.getCurrentDirectory().toString(), true);
		};
	}

}

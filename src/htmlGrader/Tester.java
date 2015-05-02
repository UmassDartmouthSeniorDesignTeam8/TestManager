package htmlGrader;
import gui.AdminGUI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;


public class Tester {
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Exam e = ExamGenerator.getExam();
//		HTMLGenerator gen = new HTMLGenerator(e);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose directory");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter filter = new FileFilter(){
			public boolean accept(File directory) {
	            return directory.getName().toLowerCase().endsWith(".pdf");
	        }

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION){
//			System.out.println(chooser.getCurrentDirectory().toString());
//			System.out.println(chooser.getSelectedFile());
//			gen.generateHTML(chooser.getCurrentDirectory().toString(), true);
//		}
			ArrayList<Response> res;
			try {
				res = QRStringInterpreter.interpret(chooser.getSelectedFile());
			} catch (Exception e) {
				System.out.println("Error loading file.");
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			for (Response r: res)
				System.out.println(r);
		}
	}
	*/
	
	public static void main(String args[]){
//		Exam e = ExamGenerator.getExam();
//		HTMLGenerator gen = new HTMLGenerator(e);
//		gen.generateHTML("C:\\Users\\Shawnski\\Desktop\\SampleOutput", true);	
		AdminGUI test = new AdminGUI();
		
	}

}

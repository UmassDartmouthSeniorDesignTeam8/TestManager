package misc;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import examData.Exam;


public class FileManager {
	public static void saveExamToFile(Exam e){
		FileOutputStream fout;
		try {
			PreferencesManager.getInstance();
			fout = new FileOutputStream(PreferencesManager.getFilePath()+"\\exams\\+e"+e.getExamID());
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(e);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
}

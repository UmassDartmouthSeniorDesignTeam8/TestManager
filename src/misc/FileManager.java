package misc;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import examData.Exam;


public class FileManager {
	public static void saveExamToFile(Exam e){
		PreferencesManager.getInstance();
		int examID;
		examID = e.getExamID();
		File file = new File(PreferencesManager.getFileDirectory()+"\\exams\\"+examID+".oex");
		while((file.exists() && !file.isDirectory())){
			examID++;
			file = new File(PreferencesManager.getFileDirectory()+"\\exams\\"+examID+".oex");
		}
		e.setExamId(examID);	
		writeObjectToFile(e, file);
	}
	
	private static void writeObjectToFile(Object o, File f){
		FileOutputStream fout;
		try{
			fout = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static Exam loadExamFromFile(int examID){
		try{
			return (Exam)loadObjectFromFile(new File(PreferencesManager.getFileDirectory()+"\\exams\\"+examID+".oex"));
		} catch (Exception ex){
			System.out.println("Error loading exam file: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	private static Object loadObjectFromFile(File f){
		InputStream file;
		try {
			file = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		InputStream buffer = new BufferedInputStream(file);
		ObjectInputStream object = null;
		try {
			object = new ObjectInputStream(buffer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		Object e = null;
		try {
			try {
				e = object.readObject();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Error loading exam file!!!" + ex.getStackTrace() + ex.getMessage());
			try {
				object.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
		try {
			object.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return e;
	}
}

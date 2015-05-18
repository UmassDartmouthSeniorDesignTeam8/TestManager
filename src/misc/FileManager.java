/**
 * This class encapsulates the saving and loading of frequently used files. The
 * directory is selected from the PreferencesManager which defaults to a directory of
 * C:\Orion\
 */

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
	/**
	 * Saves an exam file into the exams subdirectory named by the exam ID. If the
	 * existing exam file already exists, the number will be incremented until an available
	 * number is found and the exam ID will be updated in the file.
	 * @param e
	 */
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
	
	// Writes an object of any type to the given file.
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
	
	// Loads an exam that was saved via the saveExamToFile method;  returns null if it doesn't exist
	public static Exam loadExamFromFile(int examID){
		try{
			return (Exam)loadObjectFromFile(new File(PreferencesManager.getFileDirectory()+"\\exams\\"+examID+".oex"));
		} catch (Exception ex){
			System.out.println("Error loading exam file: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	// Loads a serialized object from a given file
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

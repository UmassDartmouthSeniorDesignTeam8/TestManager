package testingClasses;
import htmlGrader.Grader;
import htmlGrader.ManualGradeHandler;
import htmlGrader.ManualGrader;
import htmlGrader.Response;
import htmlGrader.ResultSet;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import documentReader.GhostRenderer;
import documentReader.QRStringInterpreter;
import examData.Exam;


public class clintdemo {
	public static void main(String args[]){
		ArrayList<BufferedImage> images = GhostRenderer.getImages("C:\\Orion\\20140506.pdf");
		//File x = new File("C:\\Orion\\newsampleexam.pdf");	// get the file
		//ArrayList<Response> responses = QRStringInterpreter.interpret(x); // call the interpreter - removed creation of object since this is static <SB>
		ArrayList<Response> responses = QRStringInterpreter.interpret(images);
		for (Response r: responses)
			System.out.println(r);
		// Load the exam from the file
		InputStream file;
		try {
			file = new FileInputStream("C:\\Orion\\exams\\e1000");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		InputStream buffer = new BufferedInputStream(file);
		ObjectInputStream object;
		try {
			object = new ObjectInputStream(buffer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		Exam e = null;
		try {
			try {
				e = (Exam)object.readObject();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Error loading exam file!!!" + ex.getStackTrace() + ex.getMessage());
			return;
		}
		try {
			object.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		BufferedImage[] array = new BufferedImage[images.size()];
		images.toArray(array);
		Grader grader = new Grader(e, responses, new ResultSet(e), array);
		grader.generateGrades();
		grader.printChosenAnswers();
	}
}

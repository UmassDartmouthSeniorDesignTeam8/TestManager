package testingClasses;
import htmlGrader.Grader;
import htmlGrader.ManualGradeHandler;
import htmlGrader.ManualGraderGUI;
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

import misc.FileManager;
import documentReader.GhostRenderer;
import documentReader.QRStringInterpreter;
import examData.Exam;


public class clintdemo {
	public static void main(String args[]) throws InterruptedException{
		ArrayList<BufferedImage> images = GhostRenderer.getImages("C:\\Orion\\test2.pdf", 120);
		//File x = new File("C:\\Orion\\newsampleexam.pdf");	// get the file
		//ArrayList<Response> responses = QRStringInterpreter.interpret(x); // call the interpreter - removed creation of object since this is static <SB>
		ArrayList<Response> responses = QRStringInterpreter.interpret(images);
		for (Response r: responses)
			System.out.println(r);
		Exam e = FileManager.loadExamFromFile(responses.get(0).getExamID());
		BufferedImage[] array = new BufferedImage[images.size()];
		images.toArray(array);
		Grader grader = new Grader(e, responses, new ResultSet(e), array);
		ResultSet rs  = grader.generateGrades();
		while (!rs.isReady())
			System.out.print(".");
		rs.printResults();
		rs.writeAnswerCSV("C:\\Orion\\results\\answers.csv");
		rs.writePointsCSV("C:\\Orion\\results\\points.csv");
		
	}
}

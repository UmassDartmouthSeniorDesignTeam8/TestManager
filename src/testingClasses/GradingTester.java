package testingClasses;
import java.awt.image.BufferedImage;
import java.io.File;

import documentReader.PDFtoImage;
import examData.Exam;


public class GradingTester {
	
	public static void main(String[] args){
		Exam textExam = ExamGenerator.getExam();
		try{
		BufferedImage images[] = PDFtoImage.convert(new File("C:\\Orion\\exam1.pdf"));
		} catch (Exception e){
			System.out.println("alyssa sucks.");
			return;
		}
	}
}

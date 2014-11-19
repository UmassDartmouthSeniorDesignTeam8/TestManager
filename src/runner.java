import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.NotFoundException;


public class runner {

	/*
	 * public static void main(String args[]){
	 
		Course course = new Course("Test Course", "Mr. Cat", "Fall 2014");
		Exam exam = new Exam("Test Exam", null, "BLAH", course);
		String[] a1 = {"Because she loves cat. ", "Because she isn't Shawn.", "Because Jesus says so"};
		MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Why is Caitlyn so awesome?",
				4, a1, 0);
		String[] a2 = {"Pickles", "Bananas", "A really cool bowtie", "Anagrams", "Auto-immune disease"};
		MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Why is Shawn so angry?", 5, a2, 2);
		exam.addQuestion(q1);
		exam.addQuestion(q2);
		exam.generateExamDocument("wtf.pdf");
	}
	*/
	
	public static void main(String args[]) throws Exception{
		
		String PATH = "input5.png";
		String[] results;
		
		File imageFile = new File(PATH);
		try{
			//BufferedImage image = PDFtoImage.convert(new File("input.pdf"));
			BufferedImage image = ImageIO.read(imageFile);
			results = QRCodeHandler.readAllCodes(image);
			for (String r: results){
				System.out.println(r);
			}
			System.out.println(results.length + " barcodes detected.");
		} catch (IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NotFoundException e){
			System.out.println("No barcodes detected");
		}
		
		/*
		QRCodeHandler.GenerateQRCode("here50.gif", "50 pixels", 50);
		QRCodeHandler.GenerateQRCode("here100.gif", "100 pixels!", 100);
		QRCodeHandler.GenerateQRCode("here150.gif", "150 pixels", 150);
		QRCodeHandler.GenerateQRCode("here25.gif", "25 pixels", 25);
		QRCodeHandler.GenerateQRCode("here10.gif", "10 pixels", 10);
		*/
	}
}

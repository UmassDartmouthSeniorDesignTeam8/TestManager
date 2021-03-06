package documentReader;

import htmlGrader.Response;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import misc.QRCodeHandler;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
/* Alyssa Tavares 
 * Orion
 * This class interprets the QR code strings 
 * read in on the exam.
 */ 
public class QRStringInterpreter 
{
	private static String[] qrStrings;
	private static int examID;
	private static int studentID;
	private static int questionNum;
	private static int answerNum;
	private static int versionNum;
	private static int x = 0;
	private static ArrayList<Response> responses = new ArrayList<Response>(); // holds the information of each QRCode
	private static ArrayList<ResultPoint[]> points; // array of the coordinates for each QRCode
	private static BufferedImage exam[];

	// changed return type so other classes can use the results <sb>
	public static ArrayList<Response> interpret(File examFile)
	{
		// convert the PDF to an image
		PDFtoImage converter = new PDFtoImage(); // create new converter object
		// array that holds all the bufferedimage pages of the exam
		try {
			exam = converter.convert(examFile);
		} catch (Exception e1) {
			System.out.println("Error converting from pdf.");
			e1.printStackTrace();
			return null;
		} // call to convert the PDF
		// returned from convert is an array of bufferedimages which are the pages of the exam
		
		/* Outside loop: iterates through the pages of the exam
		 * Inside loop: iterates through the qrCodes on that particular page
		 */
		for(int k = 0; k < exam.length; k++)
		{
			try{
				qrStrings = QRCodeHandler.readAllCodes(exam[k]);
				points = QRCodeHandler.getCoordinates();

				/* qrStrings is an array of all the qrcode strings
				 * the following loop goes through each of the strings and breaks them up
				 * with each piece of information, a new Response is created for each qrCode
				 */
				for(int i = 0; i < qrStrings.length; i++)
				{								
					String[] sep = qrStrings[i].split("v");
					// splits into two parts: [0] before the v (empty) and [1] which is the rest of the string
					
					String[] sep2 = sep[1].split("e");
					versionNum = Integer.parseInt(sep2[0]); // the actual versionNum just numbers
					
					String[] sep3 = sep2[1].split("s");
					examID = Integer.parseInt(sep3[0]); // the actual examID just numbers
					
					String[] sep4 = sep3[1].split("q");
					studentID = Integer.parseInt(sep4[0]); // the actual studentID just numbers
					
					String[] sep5 = sep4[1].split("a");
					questionNum = Integer.parseInt(sep5[0]); // the actual questionNum just numbers
					
					answerNum = Integer.parseInt(sep5[1]); // the actual answerNum just numbers
					
					// create each Response in the arrayList
					responses.add(new Response(versionNum, examID, studentID, questionNum, answerNum, k, points.get(x)));
					x++; // increase to the next set of coordinates for the next qrcode read on the exam
					
					}
			} catch (NotFoundException e){
				System.out.println("No barcodes found on page " + k);
			}
		}
			return responses;
		}
	
	public static ArrayList<Response> interpret(List<BufferedImage> exam){
		 // call to convert the PDF
		// returned from convert is an array of bufferedimages which are the pages of the exam
		
		/* Outside loop: iterates through the pages of the exam
		 * Inside loop: iterates through the qrCodes on that particular page
		 */
		for(int k = 0; k < exam.size(); k++)
		{
			System.out.print("Parsing page " + k);
			try{
				qrStrings = QRCodeHandler.readAllCodes(exam.get(k));
				System.out.println(": " + qrStrings.length + " codes found.");
				points = QRCodeHandler.getCoordinates();

				/* qrStrings is an array of all the qrcode strings
				 * the following loop goes through each of the strings and breaks them up
				 * with each piece of information, a new Response is created for each qrCode
				 */
				for(int i = 0; i < qrStrings.length; i++)
				{
					try{
						String[] sep = qrStrings[i].split("v");
						// splits into two parts: [0] before the v (empty) and [1] which is the rest of the string
						
						String[] sep2 = sep[1].split("e");
						versionNum = Integer.parseInt(sep2[0]); // the actual versionNum just numbers
						
						String[] sep3 = sep2[1].split("s");
						examID = Integer.parseInt(sep3[0]); // the actual examID just numbers
						
						String[] sep4 = sep3[1].split("q");
						studentID = Integer.parseInt(sep4[0]); // the actual studentID just numbers
						
						String[] sep5 = sep4[1].split("a");
						questionNum = Integer.parseInt(sep5[0]); // the actual questionNum just numbers
						
						answerNum = Integer.parseInt(sep5[1]); // the actual answerNum just numbers
						
						// create each Response in the arrayList
						responses.add(new Response(versionNum, examID, studentID, questionNum, answerNum, k, points.get(x)));
						x++; // increase to the next set of coordinates for the next qrcode read on the exam
					} catch (NumberFormatException ex){
						System.out.println("Error parsing string " + qrStrings[i]);
					}
						
					}
			} catch (NotFoundException e){
				System.out.println("No barcodes found on page " + k);
			}
		}
			return responses;
		}
	
	/*  the main method here is for testing purposes only */
//	public static void main(String[] args) throws Exception
//	{
//		File x = new File("C:\\Orion\\newsampleexam.pdf");	// get the file
//		QRStringInterpreter.interpret(x); // call the interpreter - removed creation of object since this is static <SB>
//		
//		for(int j = 0; j < responses.size(); j++)
//		{
//			/* 
//			 * Print checks to ensure everything was getting created correctly
//			 * displays everything but the coordinates
//			 */
//				System.out.println("Response " + j + "'s version number: " + responses.get(j).getVersionNum());
//				System.out.println("Response " + j + "'s exam id: " + responses.get(j).getExamID());
//				System.out.println("Response " + j + "'s student Id: " + responses.get(j).getStudentID());
//				System.out.println("Response " + j + "'s question num: " + responses.get(j).getQuestionNum());
//				System.out.println("Response " + j + "'s answer num: " + responses.get(j).getAnswerNum());
//				System.out.println("Response " + j + "'s page number: " + responses.get(j).getPageNum());
//				System.out.println("Response " + j + "'s coordinates:");
//				
//				// each qrCode has three coordinates, these are held in an array
//				ResultPoint[] coordinates = responses.get(j).getCoordinates();
//				for(int k = 0; k < coordinates.length; k++)
//				{
//					System.out.println(coordinates[k]); 
//				}
//				System.out.println();
//		}
//	}		
}	
	


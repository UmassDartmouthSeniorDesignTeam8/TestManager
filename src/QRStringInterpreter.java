import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import com.google.zxing.ResultPoint;
/* Alyssa Tavares 
 * Orion
 * This class interprets the QR code strings 
 * read in on the exam.
 */ 
public class QRStringInterpreter 
{
	private String[] qrStrings;
	private static String examID;
	private static String studentID;
	private static String questionNum;
	private static String answerNum;
	private static String versionNum;
	private static int x = 0;
	private static ArrayList<Response> responses = new ArrayList<Response>(); // holds the information of each QRCode
	private static ArrayList<ResultPoint[]> points; // array of the coordinates for each QRCode

	public void interpret(File examFile) throws Exception
	{
		// convert the PDF to an image
		PDFtoImage converter = new PDFtoImage(); // create new converter object
		// array that holds all the bufferedimage pages of the exam
		
		BufferedImage[] exam = converter.convert(examFile); // call to convert the PDF
		// returned from convert is an array of bufferedimages which are the pages of the exam
		
		/* Outside loop: iterates through the pages of the exam
		 * Inside loop: iterates through the qrCodes on that particular page
		 */
		for(int k = 0; k < exam.length; k++)
		{
			qrStrings = QRCodeHandler.readAllCodes(exam[k]);
			points = QRCodeHandler.getCoordinates();

			/* for each page of the exam, use qrStrings to store the read QR codes on that page
			 * and store the contents of individual QR code strings in the allCodes array. This
			 * allCodes array should hold each part of each qrCode on the exam
			 */
			for(int i = 0; i < qrStrings.length; i++)
			{								
				String[] sep = qrStrings[i].split("v");
				// splits into two parts: [0] before the v (empty) and [1] which is the rest of the string
				
				String[] sep2 = sep[1].split("e");
				versionNum = sep2[0]; // the actual versionNum just numbers
				
				String[] sep3 = sep2[1].split("s");
				examID = sep3[0]; // the actual examID just numbers
				
				String[] sep4 = sep3[1].split("q");
				studentID = sep4[0]; // the actual studentID just numbers
				
				String[] sep5 = sep4[1].split("a");
				questionNum = sep5[0]; // the actual questionNum just numbers
				
				answerNum = sep5[1]; // the actual answerNum just numbers
				
				// create each Response in the arrayList
				responses.add(new Response(versionNum, examID, studentID, questionNum, answerNum, k, points.get(x)));
				x++;
				
				}
			}
		}

	public static void main(String[] args) throws Exception
	{
		QRStringInterpreter i = new QRStringInterpreter(); // create an interpreter
		File exam = new File("exam.pdf");	// get the file
		i.interpret(exam); // call the interpreter
		
		for(int j = 0; j < responses.size(); j++)
		{
			/* 
			 * Print checks to ensure everything was getting created correctly
			 * displays everything but the coordinates
			 */
				System.out.println("Response " + j + "'s version number: " + responses.get(j).getVersionNum());
				System.out.println("Response " + j + "'s exam id: " + responses.get(j).getExamID());
				System.out.println("Response " + j + "'s student Id: " + responses.get(j).getStudentID());
				System.out.println("Response " + j + "'s question num: " + responses.get(j).getQuestionNum());
				System.out.println("Response " + j + "'s answer num: " + responses.get(j).getAnswerNum());
				System.out.println("Response " + j + "'s page number: " + responses.get(j).getPageNum());
				System.out.println("Response " + j + "'s coordinates:");
				
				// each qrCode has three coordinates, these are held in an array
				ResultPoint[] coordinates = responses.get(j).getCoordinates();
				for(int k = 0; k < coordinates.length; k++)
				{
					System.out.println(coordinates[k]); 
				}
				System.out.println();
		}
	}		
}	
	


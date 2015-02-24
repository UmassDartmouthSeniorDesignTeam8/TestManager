import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HTMLGenerator {
	private static Question[] questions;
	private static Exam exam;
	
	/*
	 * This method will generate a random ordering of questions for all
	 * of the students assigned to a class. This will be used to generate
	 * the exams.
	 */
	public Integer[][] generateRandomizedArray(Exam e){
		int numStudents = e.getCourse().getNumStudents();
		int numQuestions = e.getNumQuestions();
		Integer[][] questionNums = new Integer[numStudents][numQuestions];
		// Make an array list with normally ordered questions
		ArrayList<Integer> normalOrdering = new ArrayList<Integer>(numQuestions);
		for (int i=0; i<numQuestions; i++){
			normalOrdering.add(i);
		}
		Collections.shuffle(normalOrdering);
		for (int i=0; i<numStudents; i++){
			ArrayList<Integer> shuffledCopy = new ArrayList<Integer>(normalOrdering);
			Collections.shuffle(shuffledCopy);
			shuffledCopy.toArray(questionNums[i]);
		}		
		return questionNums;		
	}
	
	public static void generateHTML(String filePath, Exam exam){
		questions = exam.getQuestionArray();
		PrintWriter outputFile;
		try {
			outputFile = new PrintWriter(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int examID = exam.getExamID();
		int numStudents = exam.getCourse().getNumStudents();
		//Output html file header
		String pageBuilder = getPageHTMLHeader();
		pageBuilder += getCoverPage();
		questions = exam.getQuestionArray();
		for (int i=0; i<questions.length; i++){
			pageBuilder += getQuestionHTML(questions[i]);
		}
		
		/*
		 * This block generates the QR codes, which are named as their string contents. Eventually this should be delegated to
		 * a thread.
		 */
		for (int s=0; s<numStudents; s++){
			for (int q=0; q<questions.length; q++)
				if (questions[q] instanceof MultipleChoiceQuestion){
					for (int i=0; i<((MultipleChoiceQuestion) questions[q]).getNumChoices(); i++){
						String fileName = String.format("%03d%03d%03d.gif", s, q, i);
						QRCodeHandler.generateQRCode("1", exam.getExamID(), s, q, i,  fileName, 58);
					}
				}
		}
		
		// Generate the HTML for all of the questions
		for (int s=0; s<numStudents; s++){
			for (int q = 0; q<questions.length; q++){
				
			}
		}
	}
	
	public static String getPageHTMLHeader(){
		return "<html>\n	<head>\n		<title>" +
				exam.getName() +  " - " + exam.getCourse().getName() +
				"</title>\n	</head>\n";
	}
	private static String getCoverPage(){
		return "";
	}
	
	private static String getQuestionHTML(Question q){
		return "";
	}
	
	private static String getMultipleChoiceQuestionHTML(Question q){
		return " ";
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HTMLGenerator {
	protected Question[] questions;
	protected Exam exam;
	protected Integer questionNums[][];
	protected String fileDirectory;
	
	public HTMLGenerator(Exam e){
		this.exam = e;
		questions = e.getQuestionArray();
		questionNums = new Integer[e.getCourse().getNumStudents()][e.getNumQuestions()];
	}
	
	/*
	 * This method will generate the question orderings for all the exams, which will
	 * either be randomized or normal.
	 */
	protected void generateQuestionOrderings(boolean isRandomized){
		int numStudents = exam.getCourse().getNumStudents();
		int numQuestions = exam.getNumQuestions();
		
		if (isRandomized){
			// Make an array list with normally ordered questions
			ArrayList<Integer> normalOrdering = new ArrayList<Integer>(numQuestions);
			for (int i=0; i<numQuestions; i++){
				normalOrdering.add(i);
			}
			// shuffle this and make a new ordering for each exam
			Collections.shuffle(normalOrdering);
			for (int i=0; i<numStudents; i++){
				ArrayList<Integer> shuffledCopy = new ArrayList<Integer>(normalOrdering);
				Collections.shuffle(shuffledCopy);
				shuffledCopy.toArray(questionNums[i]);
			}
		}else{
			// Otherwise, generate an array with a normal ordering, which will be referenced  by all exams
			Integer[] normalQuestionOrdering = new Integer[exam.getNumQuestions()];
			for (Integer q=0; q<normalQuestionOrdering.length; q++)
				normalQuestionOrdering[q] = q;
			for (int s=0; s<exam.getCourse().getNumStudents(); s++)
				questionNums[s] = normalQuestionOrdering;			
		}

		return;		
	}
	

	/**
	 * Generates HTML files for all exams in the given directory.
	 * @param fileDirectory	- File directory where html files will go; images will go into /images/
	 * @param isRandomized - Determines whether or not each exam will have a random question ordering
	 */
	public void generateHTML(String fileDirectory, boolean isRandomized){
		this.fileDirectory = fileDirectory;
		// Randomize if needed; otherwise stock the questions array with the normal ordering
		this.generateQuestionOrderings(isRandomized);
		for (int s=0; s<exam.getCourse().getNumStudents(); s++){
			System.out.println("Generating exam " + s);
			generateSingleCopyOfExam(s, fileDirectory + "\\s" + s + ".html");
		}
		
		/*
		 * This block generates the QR codes, which are named s###q###i###. Eventually this should be delegated to
		 * a thread.
		 */
		generateQRCodes();
	}
	
	/*
	 * Generates a single copy of the HTML exam.
	 */
	protected void generateSingleCopyOfExam(int studentNum, String fileLocation){
		PrintWriter outputFile;
		try{
			outputFile = new PrintWriter(new File(fileLocation));
			System.out.println(fileLocation);
		} catch (Exception e){
			System.out.println("Error generating exam for student " + studentNum);
			System.out.println(e.getMessage());			
			return;
		}
		String pageBuilder = getPageHTMLHeader();
		pageBuilder += getCoverPage();
		for (int questionNum=0; questionNum<questions.length; questionNum++)
			pageBuilder += getQuestionHTML(questions[questionNum], questionNum, studentNum);
		pageBuilder += getPageEnding();
		outputFile.print(pageBuilder);
		outputFile.close();
	}
	
	/*
	 * Generates all of the QR codes needed by an exam.
	 */
	protected void generateQRCodes(){
		try{
			File newDirectory = new File(fileDirectory + "\\images\\");
			System.out.println(newDirectory);
			if (!newDirectory.mkdirs()){
				System.out.println("Could not create images directory");
			}
		} catch (Exception e){
			System.out.println("Error generating images directory. " + e.getMessage());
		}
		if (questions==null)
			return;
		else{
			for (int s=0; s<exam.getCourse().getNumStudents(); s++){
				for (int q=0; q<questions.length; q++){
					if (questions[1]!=null){
						if (questions[q] instanceof MultipleChoiceQuestion){
							for (int i=0; i<((MultipleChoiceQuestion) questions[q]).getNumChoices(); i++){
								String fileName = getQRCodePath(s, q, i);
								QRCodeHandler.generateQRCode(getQRCodePath(s,q,i), 1, exam.getExamID(), s, q, i, 28);
							}
						}
					}
				}
			}
		}
	}
	
	protected String getPageHTMLHeader(){
		return "<html>\n	<head>\n<link rel =\"stylesheet\" type = \"text/css\" href=\"basic.css\"><title>" +
				exam.getName() +  " - " + exam.getCourse().getName() +
				"</title>\n	</head>\n <body>\n";
	}
	
	protected String getPageEnding(){
		return "</body>\n</html>\n";
	} 
	protected String getCoverPage(){
		// TODO
		return "";
	}
	
	protected String getQuestionHTML(Question q, int question, int student){
		if (q instanceof MultipleChoiceQuestion)
			return getMultipleChoiceQuestionHTML((MultipleChoiceQuestion)q, question, student);
		return "";
	}
	
	protected String getMultipleChoiceQuestionHTML(MultipleChoiceQuestion q, int questionNum, int student){
		// First write the question frame, question text Frame and question text
		String working = "<div class = \"questionFrame multipleChoiceQuestionFrame\">\n" +
						 "<div class = \"questionTextFrame multipleChoiceQuestionTextFrame\">\n" +
						 "<p class = \"questionText multipleChoiceQuestionText\">\n" +
						 "<em class = \"questionNumber\">" + (questionNum+1) + ". </em>" +
						 questions[questionNum].getQuestionText() + 
						 "<em class = \"pointValue\">(" + questions[questionNum].getPointValue() + " points)</em></p>\n</div>";
		String[] choices = q.getChoices();
		// Create frames for all the responses
		for (int i=0; i<choices.length; i++){
			working+="<div class=\"responseFrame multipleChoiceResponseFrame\">\n" +
					 getQRCodeHTML(student, questionNum, i) +
					"<div class =\"responseText\"><p class=\"hangingIndent\"><em class = \"responseLetter multipleChoiceResponseLetter\">" + (char)('A' + i) +
					"</em>" + choices[i] + "</p>\n</div></div>";
		}
		// End frame after all responses have been populated
		working += "</div>";
		return working;
	}
	
	protected String getQRCodePath(int student, int question, int response){
		return String.format(fileDirectory + "\\images\\s%03dq%03di%03d.gif", student, question, response);
	}
	
	protected String getQRCodeHTML(int student, int questionNum, int response){
		return "<div class = \"responseImageFrame\"><img class = \"qrCode\" src = \"" + getQRCodePath(student, questionNum, response) + "\"/></div>\n";
	}
}

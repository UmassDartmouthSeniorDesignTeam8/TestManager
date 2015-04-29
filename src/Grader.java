import java.util.ArrayList;
import java.util.Arrays;


/*
 * This class compares responses to the answer key (exam class) to generate final grades.
 * It also pushes items to the manual grade handler so that they may be resolved.
 */
public class Grader {
	public final static int NO_RESPONSE = -2;
	public final static int UNKNOWN_ANSWER = -1;
	
	private Exam exam;
	private ArrayList<Response> responses;
	private ArrayList<Response> erroneousResponses;
	private ManualGradeHandler handler;
	private int chosenAnswers[][];
	private int points[][];
	
	/**
	 * The grader class associates a list of responses with the exam itself.
	 * This will be used to generate the actual grades and manually handled
	 * items, such as open response questions.
	 * @param e
	 * @param r
	 * @param h
	 */
	public Grader(Exam e, ArrayList<Response> r, ManualGradeHandler h){
		this.exam = e;
		this.responses = r;
		this.handler = h;
		chosenAnswers = new int[exam.getNumPrinted()][exam.getNumQuestions()];
		points = new int[exam.getNumPrinted()][exam.getNumQuestions()];
		erroneousResponses = new ArrayList<Response>();
	}
	
	public void generateGrades(){
		// first declare a jagged array to hold the possible responses for all questions [student][question][response]
		boolean[][][] qrCodesFound = new boolean[exam.getNumPrinted()][exam.getNumQuestions()][];
		Question questions[] = exam.getQuestionArray();
		RectangleBoundary[][] questionBoundaries = new RectangleBoundary[exam.getNumPrinted()][exam.getNumQuestions()];
		
		// now declare the appropriate size for each question in the array; by default all booleans will be false
		for (int q=0; q<exam.getNumQuestions(); q++){
			for (int s=0; s<exam.getNumPrinted(); s++){
				if (questions[q].isManuallyGraded())
						qrCodesFound[s][q]= new boolean[0];
				else
						qrCodesFound[s][q] = new boolean[questions[q].getNumChoices()];
			}
		}
		//now we go through all the questions and fill them into the array where necessary
		for (Response r: responses){
			try{
				// Create item for open response question
				if (r.isOpenResponse())
					handler.addNewOpenResponseItem(r.getStudentID(), r.getQuestionNum(), questions[r.getQuestionNum()], new RectangleBoundary(r.getCoordinates(), r.getPageNum()));
				// Create item when student's name barcode is identified
				else if (r.isStudentIdentifier())
					handler.addNewStudentIdentifier(r.getStudentID(), new RectangleBoundary(r.getCoordinates(), r.getPageNum()));
				// Otherwise barcode is from multiple choice
				// if duplicated, generate warning message
				else if(qrCodesFound[r.getStudentID()][r.getQuestionNum()][r.getAnswerNum()] == true){
						handler.addNewMultipleChoiceItem(r.getStudentID(), r.getQuestionNum(), (MultipleChoiceQuestion)questions[r.getQuestionNum()], ManualGradeHandler.DUPLICATE_QR_CODE_FOUND, new RectangleBoundary(r.getCoordinates(), r.getPageNum()));
				}else{
					// Mark the QR code as found in the 3d array
					qrCodesFound[r.getStudentID()][r.getQuestionNum()][r.getAnswerNum()] = true;	
					// Add the qr code's coordinates to the boundary for the question in case it's needed later; establish the object if it's null
					if (questionBoundaries[r.getStudentID()][r.getQuestionNum()] == null)
						questionBoundaries[r.getStudentID()][r.getQuestionNum()] = new RectangleBoundary(r.getCoordinates(), r.getPageNum());
					else
						questionBoundaries[r.getStudentID()][r.getQuestionNum()].addPoints(r.getCoordinates());
				}
			} catch (ArrayIndexOutOfBoundsException ex){
					erroneousResponses.add(r);
			}
		}
		// Once they have all been filled, it's time to check every question to see that it has the right number of responses
		for (int s = 0; s<qrCodesFound.length; s++){
			for (int q = 0; q<qrCodesFound[s].length; q++){
				// Sum up how many QR codes were found for this particular section
				int responsesForThisQuestion = 0, chosenResponse = 0;
				for (int r = 0; r<qrCodesFound[s][q].length; r++)
					// If the code is found, increment the count. Otherwise, it is the chosen response assuming there is no error
					if (qrCodesFound[s][q][r])
						responsesForThisQuestion++;
					else
						chosenResponse = r;
				// If this is not one less than the number of responses, generate an error
				if (responsesForThisQuestion == 0){
					// No QR codes found (ie page not scanned)
					handler.addNewMissingItem(s, q, questions[q]);
					chosenAnswers[s][q] = UNKNOWN_ANSWER;
				} else if (responsesForThisQuestion == qrCodesFound[s][q].length)
					chosenAnswers[s][q] = NO_RESPONSE;
					// In this case, no answer was selected
				else if (responsesForThisQuestion+1!=qrCodesFound[s][q].length){
					// 2 or more barcodes undetected = indicates uncertain answer
					chosenAnswers[s][q] = UNKNOWN_ANSWER;
					handler.addNewMultipleChoiceItem(s, q, questions[q], ManualGradeHandler.INDETERMINATE_RESPONSE, questionBoundaries[s][q]);						
				} else {
					// otherwise, the answer is definitively known and can be recorded
					chosenAnswers[s][q] = chosenResponse;
					points[s][q] = questions[q].getPointValue();
				}
					
			}
		}
	}
	
	public void printChosenAnswers(){
		int count = 0;
		for (int[] i:chosenAnswers){
			System.out.println("Student "+ count++ + " responses: " + Arrays.toString(i));
		}
	}
}

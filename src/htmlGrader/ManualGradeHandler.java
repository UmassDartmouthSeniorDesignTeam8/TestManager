package htmlGrader;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Queue;

import examData.MultipleChoiceQuestion;
import examData.Question;

public class ManualGradeHandler {
	public final static int DUPLICATE_QR_CODE = 1;
	public final static int INDETERMINATE_RESPONSE = 2;
	public final static int NO_QR_CODES_FOUND = 3;
	private Queue<ManuallyGradedItem> itemsToGrade;
	private Queue<MissingItem> missingItems;
	private ManualGrader grader;
	private ManuallyGradedItem currentlyGrading;
	private Queue<ManuallyGradedExceptionItem> exceptions;
	private boolean allExceptionsReceived;
	private ResultSet results;
	private BufferedImage[] images;
	
	public ManualGradeHandler(BufferedImage[] pages, ResultSet results){
		this.results = results;
		itemsToGrade = new PriorityQueue<ManuallyGradedItem>();
		missingItems = new PriorityQueue<MissingItem>();
		images = pages;
	}
	
	/**
	 * Adds a new manually graded open response question to be handled by the manual grading interface.
	 * @param student			Student number
	 * @param questionNum		Question number
	 * @param q					The question itself
	 * @param boundary			The rectangle boundary of the question in the document
	 */
	public void addNewOpenResponseItem(int student, int questionNum, Question q, RectangleBoundary boundary){
		
	}
	
	/**
	 * This method will add an item when none of the QR codes for a question were detected. This could
	 * indicate a page that was not read properly.
	 * 
	 * @param studentNum	Student number
	 * @param questionNum	Question number
	 * @param q				The question
	 */
	public void addNewMissingItem(int studentNum, int questionNum, Question q){
		missingItems.add(new MissingItem(studentNum, questionNum, q));
	}
	
	/**This is used when a multiple choice question cannot be graded automatically. For example,
	 * if two of the QR codes do not appear, there is no certainty regarding the response.
	 * 
	 * @param studentNum	Student number
	 * @param questionNum	Question number
	 * @param q				The question that is being manually graded
	 * @param boundary		The rectangle boundary containing coordinates
	 * @param reason		Reason the item must be manually graded (see public constants)
	 */
	public void addNewMultipleChoiceItem(int studentNum, int questionNum, Question q, RectangleBoundary boundary, int reason){
		// If the question isn't Multiple Choice, then there is a problem, don't attempt to grade
		if (!(q instanceof MultipleChoiceQuestion))
			exceptions.add(new ManuallyGradedExceptionItem(studentNum, questionNum, ManuallyGradedExceptionItem.NOT_MULTIPLE_CHOICE));
		// If no QR codes were found, add to missing items
		if (reason==NO_QR_CODES_FOUND){
			addNewMissingItem(studentNum, questionNum, q);
		}
		// Otherwise, create an item and add it to the queue
		itemsToGrade.add(new ManuallyGradedMCItem(studentNum, boundary, questionNum, (MultipleChoiceQuestion)q, reason));
		if (currentlyGrading==null){
			displayNext();
		}			
	}
	
	public void addNewStudentIdentifier(int student, RectangleBoundary boundary){
		itemsToGrade.add(new ManuallyGradedStudentID(student, boundary));
	}
	
	// Display the next item in the manual grader GUI
	private synchronized void displayNext(){
		currentlyGrading = itemsToGrade.poll();
		if (currentlyGrading!=null){
			// add next item to manual grader
			grader = new ManualGrader(images[currentlyGrading.getRectangleBoundary().getPageNum()],currentlyGrading.getQuestion().getNumChoices(), currentlyGrading.getRectangleBoundary());
		} else if (allExceptionsReceived){
			// TODO - show Result Set GUI
		}
	}
	
	/**
	 * This will receive feedback back from the manual grader GUI. It will ensure that the correct result is provided.
	 * @param points				Number of points for open response; NO_RESPONSE otherwise
	 * @param responseNumber		MC response item, -1 for unknown; NO_RESPONSE otherwise
	 * @param studentName			Student name as string for student identifiers; null/empty otherwise
	 * @return
	 */
	public synchronized boolean getResponseFromGUI(int points, int responseNumber, String studentName){
		if (currentlyGrading.requiresStudentID() && (studentName==null || studentName.length() == 0)){
			results.provideStudentName(currentlyGrading.getStudentNum(), studentName);
			displayNext();
			return true;
		} else if (currentlyGrading.requiresStudentID()){
			grader.setErrorMessage("Student name requires to satisfy student identifier. A name must be chosen");
			return false;
		} else if (currentlyGrading.requiresPoints() && points!=ManualGrader.NO_RESPONSE){
			results.provideMCResult(currentlyGrading.getStudentNum(), currentlyGrading.getQuestionNum(), points);
			displayNext();
			return true;
		} else if (currentlyGrading.requiresPoints()){
			grader.setErrorMessage("Point value must be assigned for this question.");
		} else if (currentlyGrading.requiresResponse() && responseNumber == ManualGrader.NO_RESPONSE)
			grader.setErrorMessage("Mulitple choice response must be selected.");
		else if (currentlyGrading.requiresResponse()){
			results.provideMCResult(currentlyGrading.getStudentNum(), currentlyGrading.getQuestionNum(), responseNumber);
			displayNext();
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used by the Grader to notify the manual grade handler
	 * that there will be no more exception items generated so that it will
	 * know when to terminate the manual grading process.
	 */
	public void notifyAllExceptionsReceived(){
		allExceptionsReceived = true;
		for (int i=0; i<10; i++)
			displayNext();
	}
	
	/**
	 * This allows the user to skip all remaining exceptions, rewarding
	 * no points for any remaining items.
	 */
	public void skipRemainingExceptions(){
		
	}
	
	/**
	 * This permanently skips the current exception, rewarding no points.
	 */
	public void skipCurrentException(){
		displayNext();
	}
}

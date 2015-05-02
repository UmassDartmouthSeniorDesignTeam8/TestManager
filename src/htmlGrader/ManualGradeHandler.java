package htmlGrader;
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
	
	public ManualGradeHandler(){
		
		itemsToGrade = new PriorityQueue<ManuallyGradedItem>();
		missingItems = new PriorityQueue<MissingItem>();
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
			exceptions.add(new ManuallyGradedExceptionItem(studentNum, questionNum, ManuallyGradedExceptionItem.Reason.NOT_MULTIPLE_CHOICE));
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
	public synchronized void displayNext(){
		if (currentlyGrading==null){
			currentlyGrading = itemsToGrade.poll();
			if (currentlyGrading!=null){
				// add next item to manual grader
				grader = new ManualGrader(null /*replace with image*/,currentlyGrading.getQuestion().getNumChoices(), currentlyGrading.getRectangleBoundary());
			}
		}
	}
	
	/*
	 * Accept a response from the GUI; this will provide only small validation
	 */
	public synchronized void getResponseFromGUI(int points, int responseNumber, String studentName){
		
	}
}

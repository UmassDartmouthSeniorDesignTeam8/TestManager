import java.util.PriorityQueue;
import java.util.Queue;

public class ManualGradeHandler {
	public final static int DUPLICATE_QR_CODE = 1;
	public final static int INDETERMINATE_RESPONSE = 2;
	public final static int NO_QR_CODES_FOUND = 3;
	private Queue<ManuallyGradedItem> itemsToGrade;
	private Queue<MissingItem> missingItems;
	private ManualGrader grader;
	private ManuallyGradedItem currentlyGrading;
	
	public ManualGradeHandler(ManualGrader grader){
		this.grader = grader;
		itemsToGrade = new PriorityQueue<ManuallyGradedItem>();
		missingItems = new PriorityQueue<MissingItem>();
	}
	
	public void addNewOpenResponseItem(int student, int questionNum, Question q, RectangleBoundary boundary){
		
	}
	
	public void addNewMissingItem(int studentNum, int questionNum, Question q){
		missingItems.add(new MissingItem(studentNum, questionNum, q));
	}
	
	public void addNewMultipleChoiceItem(int studentNum, int questionNum, Question q, RectangleBoundary boundary, int reason){
		// If the question isn't Multiple Choice, then there is a problem, don't attempt to grade
		if (!(q instanceof MultipleChoiceQuestion))
			return;
		// If no QR codes were found, add to missing items
		if (reason==NO_QR_CODES_FOUND){
			addNewMissingItem(studentNum, questionNum, q);
		}
		// Otherwise, create an item and add it to the queue
		itemsToGrade.add(new ManuallyGradedMCItem(studentNum, boundary, questionNum, (MultipleChoiceQuestion)q, reason));
		if (currentlyGrading==null){
			
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
			}
		}
	}
	
	/*
	 * Accept a response from the GUI; this will provide only small validation
	 */
	public synchronized void getResponseFromGUI(int points, int responseNumber, String studentName){
		
	}
}

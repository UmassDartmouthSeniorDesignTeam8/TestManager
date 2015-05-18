package htmlGrader;
import examData.Question;

/**
 * ManuallyGradedItems are all items that require intervention from the user in order
 * to assign a multiple choice response/points.
 * @author Shawn
 *
 */
public abstract class ManuallyGradedItem implements Comparable<ManuallyGradedItem>{
	private RectangleBoundary boundary;
	private int student;
	private Question question;
	private int questionNum;
	// used when the answer is unclear (more than 1 barcode missing)
	public final int ANSWER_NOT_IDENTIFIED = 0;
	// used when the question is open response originally
	public final int OPEN_RESPONSE_QUESTION = 1;
	// used when the same barcode is read twice, indicating possible duplicates
	public final int DUPLICATE_QRCODES_DETECTED = 2;
	// used when the QR code near a student's name is identified
	public final int STUDENT_NAME = 3;
	// used when no QR codes are found
	public final int NO_QR_CODES_DETECTED_FOR_QUESTION = 4;
	
	/**
	 * 
	 * @param student		student number in the question array
	 * @param question		question number in the question array, -1 if it identifies a student name
	 * @param boundingBox	2x2 array with 2 x,y coordinates
	 * @param numChoices	-1 for non-multiple choice questions; number of choices otherwise
	 * @param reason		reason code; constants available inside class
	 * @param maxPoints		For open response questions, specify the number of points possible; otherwise -1
	 */
	public ManuallyGradedItem(int studentNum, RectangleBoundary boundary, int questionNum, Question q){
		this.student = studentNum;
		this.boundary = boundary;
		this.question = q;
	}
	
	public RectangleBoundary getRectangleBoundary(){
		return boundary;
	}

	public int getStudentNum() {
		return student;
	}
	
	public Question getQuestion(){
		return question;
	}
	
	public abstract int getQuestionNum();

	public abstract String getInstructions();
	
	// For the sake of natural ordering, items should be grouped according to the question Number
	public int compareTo(ManuallyGradedItem b){
		if (questionNum<b.questionNum)
			return -1;
		else if (questionNum>b.questionNum)
			return 1;
		else
			return 0;
	}
	
	public abstract boolean requiresPoints();
	
	public abstract boolean requiresResponse();
	
	public abstract boolean requiresStudentID();
}


public abstract class ManuallyGradedItem {
	private int[][] boundingBox = new int[2][2];
	int student, question;
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
	private int choiceSelected, pointsAwarded;
	private int reason;
	private int maxPoints;
	
	/**
	 * 
	 * @param student		student number in the question array
	 * @param question		question number in the question array, -1 if it identifies a student name
	 * @param boundingBox	2x2 array with 2 x,y coordinates
	 * @param numChoices	-1 for non-multiple choice questions; number of choices otherwise
	 * @param reason		reason code; constants available inside class
	 * @param maxPoints		For open response questions, specify the number of points possible; otherwise -1
	 */
	public ManuallyGradedItem(int student, int question, int boundingBox[][], int numChoices, int reason, int maxPoints){
		this.student = student;
		this.question = question;
		this.boundingBox = boundingBox;
		this.reason = reason;
		this.maxPoints = maxPoints;
	}
	
	public int getTopLeftX(){
		return boundingBox[0][0];
	}
	
	public int getTopLeftY(){
		return boundingBox[0][1];
	}
	
	public int getBottomRightX(){
		return boundingBox[1][0];
	}
	
	public int getBottomRightY(){
		return boundingBox[1][1];
	}

	public int getStudent() {
		return student;
	}

	public int getQuestion() {
		return question;
	}
	
	public String getInstructions(){
		switch (reason){
			case ANSWER_NOT_IDENTIFIED:
			case DUPLICATE_QRCODES_DETECTED:
				return "Please select the student's response to the highlighted question. ";
			case STUDENT_NAME:
				return "Please identify the student whose name is written near the selected QR code.";
			case OPEN_RESPONSE_QUESTION:
				return "Please grade the open response question on a scale from 0 to " + maxPoints +".";
		}
		return "Item requires manual grading. Reason unknown.";
	}
	
	/**
	 * Only of of these attributes should have a valid value when an answer is resolved.
	 * @param choice		-1 for non-multiple choice questions; choice of the response otherwise (0=A)
	 * @param pointsAwarded -1 for multiple-choice questions; number of points awarded otherwise
	 */
	public void resolve(int choice, int pointsAwarded){
		this.choiceSelected = choice;
		this.pointsAwarded = pointsAwarded;
		
	}
}

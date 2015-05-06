package htmlGrader;
/**
 * This class is used by the manual grade handler to hold information regarding items that cause
 * exceptions. This occurs, for example, when a multiple choice response item is added for a question that
 * is not multiple choice.
 * @author Shawn Beaudoin
 *
 */
public class ManuallyGradedExceptionItem {
	
	public static final int NOT_MULTIPLE_CHOICE = -1, NOT_OPEN_RESPONSE = -2; 
	private int student, question;
	private int reason;
	
	/**
	 * 
	 * @param student		Student number
	 * @param question		Question number
	 * @param reason		Reason this value was generated (enumerated class)
	 */
	public ManuallyGradedExceptionItem(int student, int question, int reason) {
		this.question = question;
		this.reason = reason;
		this.student = student;
	}

	public int getReason() {
		return reason;
	}

	public int getStudent() {
		return student;
	}

	public int getQuestion() {
		return question;
	}

	@Override
	public String toString() {
		return "ManuallyGradedExceptionItem [reason=" + reason + ", student="
				+ student + ", question=" + question + "]";
	}
	
}

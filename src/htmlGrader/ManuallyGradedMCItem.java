package htmlGrader;
import examData.MultipleChoiceQuestion;


public class ManuallyGradedMCItem extends ManuallyGradedItem {
	private final int DUPLICATE_QR_CODE = ManualGradeHandler.DUPLICATE_QR_CODE;
	private final int INDETERMINATE_RESPONSE = ManualGradeHandler.INDETERMINATE_RESPONSE;
	private final int NO_QR_CODES_FOUND = ManualGradeHandler.NO_QR_CODES_FOUND;
	private int questionNum;
	private int reason;

	public ManuallyGradedMCItem(int studentNum, RectangleBoundary boundary, int questionNum, MultipleChoiceQuestion q, int reason) {
		super(studentNum, boundary, questionNum, q);
		this.questionNum = questionNum;
		this.reason = reason;
	}

	@Override
	public int getQuestionNum() {
		return questionNum;
	}

	@Override
	public String getInstructions() {
		switch (reason){
			case DUPLICATE_QR_CODE:
					return "Duplicate QR codes read for this question. Please confirm response.";
			case INDETERMINATE_RESPONSE:
					return "Response could not be determined. Please verify the chosen response.";
			case NO_QR_CODES_FOUND:
					return "No QR codes were found for this student and question.";
		}
		return "Unknown multiple choice exception.";
	}

	@Override
	public boolean requiresPoints() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requiresResponse() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean requiresStudentID() {
		// TODO Auto-generated method stub
		return false;
	}

}

import com.google.zxing.ResultPoint;


public class ManualGradeHandler {
	public final static int DUPLICATE_QR_CODE_FOUND = 1;
	public final static int MULTIPLE_CHOICE_ANSWER_INDETERMINATE = 2;
	public final static int NO_QR_CODES_FOUND_FOR_QUESTION = 3;
	
	public void addNewOpenResponseItem(int student, int questionNum, Question q, RectangleBoundary boundary){
	}
	
	public void addNewMultipleChoiceItem(int student, int questionNum, Question q, int reasonCode, RectangleBoundary boundary){		
	}
	
	public void addNewStudentIdentifier(int student, RectangleBoundary boundary){
		
	}
	
	public void addNewMissingItem(int student, int question, Question q){
		
	}
}


public class ManuallyGradedStudentID extends ManuallyGradedItem {
	
	public ManuallyGradedStudentID(int studentNum, RectangleBoundary boundary){
		super(studentNum, boundary, -1, null);
	}

	@Override
	public int getQuestionNum() {
		return -1;
	}

	@Override
	public String getInstructions() {
		return "Please identify the student for this exam.";
	}

}

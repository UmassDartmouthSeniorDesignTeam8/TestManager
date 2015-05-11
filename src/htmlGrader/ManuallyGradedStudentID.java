package htmlGrader;

import examData.OpenResponseQuestion;
import examData.Question;


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

	@Override
	public boolean requiresPoints() {
		return false;
	}

	@Override
	public boolean requiresResponse() {
		return false;
	}

	@Override
	public boolean requiresStudentID() {
		return true;
	}
	
	@Override
	public Question getQuestion(){
		return new OpenResponseQuestion("Please identify the student.", 0);
	}

}

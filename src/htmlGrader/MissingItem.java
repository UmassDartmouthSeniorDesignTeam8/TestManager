package htmlGrader;
import examData.Question;


public class MissingItem {
	private int questionNum, studentNum;
	private Question question;
	
	public MissingItem(int qNo, int sNo, Question question){
		questionNum = qNo;
		studentNum = sNo;
		this.question = question;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public int getStudentNum() {
		return studentNum;
	}
	
	public Question getQuestion(){
		return question;
	}
}

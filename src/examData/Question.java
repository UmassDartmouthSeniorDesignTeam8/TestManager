package examData;
import java.io.Serializable;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String question_text;
	private int point_value;

	public Question(String questionText, int pointValue){
		this.question_text = questionText;
		this.point_value = pointValue;
	}

	public abstract boolean isManuallyGraded();

	public int getPointValue() {
		return point_value;
	}

//	public void setPointValue(int pointValue) {
//		this.point_value = pointValue;
//	}
//
	public String getQuestionText() {
		return question_text;
	}
	
	public abstract int getNumChoices();

//	public void setQuestionText(String questionText) {
//		this.question_text = questionText;
//	}


}

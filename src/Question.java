import java.io.Serializable;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String question_text;
	private int point_value;

	private int quest_id;
	private String answer;

	public Question(String question_text, int point_value) {
		this.question_text = question_text;
		this.point_value = point_value;
	}

	public abstract boolean isManuallyGraded();

	public int getPointValue() {
		return point_value;
	}

	public void setPointValue(int pointValue) {
		this.point_value = pointValue;
	}

	public String getQuestionText() {
		return question_text;
	}

	public void setQuestionText(String questionText) {
		this.question_text = questionText;
	}


}

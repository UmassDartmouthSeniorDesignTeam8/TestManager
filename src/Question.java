import java.io.Serializable;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String question_text;
	private int point_value;
	private int question_id;

	private int exam_id;
	
	public Question(int question_id, String question_text, int point_value, int exam_id) {
		this.question_id = question_id;
		this.question_text = question_text;
		this.point_value = point_value;
		this.exam_id = exam_id;
	}
	
	/*how can you make a question without a question_id & exam_id??*/
	public Question(String questionText, int pointValue){
		this.question_text = questionText;
		this.point_value = pointValue;
	}

	public abstract boolean isManuallyGraded();

	public int getQuestion_id() {
		return question_id;
	}

	public int getExam_id() {
		return exam_id;
	}

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

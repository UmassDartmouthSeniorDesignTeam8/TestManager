import java.io.Serializable;

import com.lowagie.text.Paragraph;


public abstract class Question implements Serializable{

	private static final long serialVersionUID = -8272622275712458557L;
	private String questionText;
	private int pointValue;
	
	public Question(String questionText, int pointValue) {
		super();
		this.questionText = questionText;
		this.pointValue = pointValue;
	}
	
	public abstract boolean isManuallyGraded();
	
	public abstract Paragraph[] generateFormattedPDFSection();
	
	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
}

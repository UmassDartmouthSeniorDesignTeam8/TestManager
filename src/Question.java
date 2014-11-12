import java.io.Serializable;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Section;


public abstract class Question implements Serializable{

	private static final long serialVersionUID = -8272622275712458557L;
	private String questionText;
	private int pointValue;
	private Chapter section;
	
	public Question(String questionText, int pointValue) {
		super();
		this.questionText = questionText;
		this.pointValue = pointValue;
	}
	
	public abstract boolean isManuallyGraded();
	
	public abstract void generateFormattedPDFSection();
	
	public Chapter getFormattedPDFSection(){
		if (section == null)
			generateFormattedPDFSection();
		return section;
	}
	
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
	
	protected void setFormattedPDFSection(Chapter section){
		this.section = section;
	}
}

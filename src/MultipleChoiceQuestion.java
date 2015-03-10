import java.util.ArrayList;


public class MultipleChoiceQuestion extends Question {
	
	private static final long serialVersionUID = -29356089960775464L;
	public final int MAX_RESPONSES = 10;
	private String choices[];
	private int correctChoice;

	private int question_id;
	private int exam_id;
	private String question_text;
	private int answer_id;
	private int question_type; //don't think it's needed in here. 
	private int point_value;
	
	
	/**
	 * 
	 * @param questionText		The text of the question.
	 * @param pointValue		The number of points awarded for a correct answer.
	 * @param choices			An array of strings of possible choices.
	 * @param correctChoice		Index of the correct answer within the choices array.
	 */
	
	public MultipleChoiceQuestion(int question_id, int exam_id, String question_text, int point_value){
		super(question_text, point_value); //is this needed because I couldn't get it working.... 
		this.question_id = question_id;
		this.exam_id = exam_id;
		this.question_text = question_text; //but it works with these lines added in so... 
		this.point_value = point_value;
	}
	
	
	
	
	
	public MultipleChoiceQuestion(String questionText, int pointValue, String choices[], int correctChoice){
		super(questionText, pointValue);
		if (choices.length > MAX_RESPONSES||correctChoice>=choices.length)
			throw new IllegalArgumentException("Multiple choice questions may have no more than " +
							MAX_RESPONSES + " choices. Correct choice must be a valid index of an element " +
							"in the choices array.");
		else {
			this.choices = choices;
			this.correctChoice = correctChoice;
		}
	}
	
	/**
	 * 
	 * @param response		Index of the chosen response.
	 * @return				Returns the point value if correct; 0 otherwise.
	 */
	public int gradeResponse(int response){
		if (response==correctChoice)
			return getPointValue();
		return 0;
	}
	
	public String[] getChoices(){
		return choices;
	}
	
	public int getNumChoices(){
		return choices.length;
	}

	@Override
	public boolean isManuallyGraded() {
		return false;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Answer: question_id= " );
		b.append(question_id);
		b.append(" exam_id = >");
		b.append(exam_id);
		b.append("< question_text = ");
		b.append(question_text);
		b.append("< point_value = ");
		b.append(point_value);
		return b.toString();
	}

}

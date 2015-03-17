import java.util.ArrayList;


public class MultipleChoiceQuestion extends Question {
	
	private static final long serialVersionUID = -29356089960775464L;
	public final int MAX_RESPONSES = 10;
	private ArrayList<String> choices;
	private int correctChoice;


	private int answer_id;
	private ArrayList<String> response_text;
	
	/**
	 * 
	 * @param questionText		The text of the question.
	 * @param pointValue		The number of points awarded for a correct answer.
	 * @param choices			An array of strings of possible choices.
	 * @param correctChoice		Index of the correct answer within the choices array.
	 */
	
//	public MultipleChoiceQuestion(int question_id, int exam_id, String question_text, int point_value, ArrayList<String> response_text){
	/*why is repsponse_text going to the super class?? <co>*/
	//		super(question_text, point_value, response_text.toArray() );  
//		this.response_text = response_text;
//	}
	
	
	
	
	
	public MultipleChoiceQuestion(int question_id, int exam_id, String questionText, int pointValue, ArrayList<String> choices, int correctChoice){
		super(question_id, questionText, pointValue, exam_id);
		if (choices.size() > MAX_RESPONSES||correctChoice>=choices.size())
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
	/*gives the string array that was wanted <co>*/
	public String[] getChoices(){
		return (String[]) choices.toArray();
	}
	
	public int getNumChoices(){
		return choices.size();
	}

	@Override
	public boolean isManuallyGraded() {
		return false;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Answer: question_id= " );
		b.append(getQuestion_id());
		b.append(" exam_id = >");
		b.append(getExam_id());
		b.append("< question_text = ");
		b.append(getQuestionText());
		b.append("< point_value = ");
		b.append(getPointValue());
		b.append("< responses = ");
		b.append(response_text);
		return b.toString();
	}

}

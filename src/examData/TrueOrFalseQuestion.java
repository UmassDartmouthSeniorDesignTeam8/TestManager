package examData;
import java.util.ArrayList;


public class TrueOrFalseQuestion extends MultipleChoiceQuestion {

	private static final long serialVersionUID = -29356089960775464L;
	public final int MAX_RESPONSES = 2;
	// This is established as a static variable to save on memory
	private static String[] choices = {"True", "False"};
//	private String choices[];
//	private int correctChoice;
//
//	private int question_id;
//	private int exam_id;
//	private String question_text;
//	private int answer_id;
//	private int question_type; //don't think it's needed in here. 
//	private int point_value;
//	Overshadowed variables are overwritten.
// 	Removed constructor because exam and question ID aren't necessary <sb>	
//	public TrueOrFalseQuestion(int question_id, int exam_id, String question_text, int point_value){
//		super(question_id, question_text, point_value, exam_id); 
//	}

	// This will call the multiple choice constructor and leaves choices null (since they are static)
	//public TrueOrFalseQuestion(int question_id, int exam_id, String questionText, int pointValue, int correctChoice){
	public TrueOrFalseQuestion(String questionText, int pointValue, int correctChoice){
		super(questionText, pointValue, null, correctChoice);
		/*I deleted question_id and exam_id because there not going to be used in the actual program 
		 * changed from: 
		 * 			super(question_id, exam_id, questionText, pointValue, null, correctChoice);*/
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("< question_text = ");
		b.append(getQuestionText());
		b.append("< point_value = ");
		b.append(getPointValue());
		return b.toString();
	}

	@Override
	public boolean isManuallyGraded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	// 2 choices only: true or false
	public int getNumChoices(){
		return choices.length;
	}
	
	@Override
	// returns an array containing true and false
	public String[] getChoices(){
		return choices;
	}
	
	
}

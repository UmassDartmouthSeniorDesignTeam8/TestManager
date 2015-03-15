
public class TrueOrFalseQuestion extends Question {

	private static final long serialVersionUID = -29356089960775464L;
	public final int MAX_RESPONSES = 2;
	private String choices[];
	private int correctChoice;

	private int question_id;
	private int exam_id;
	private String question_text;
	private int answer_id;
	private int question_type; //don't think it's needed in here. 
	private int point_value;
	
	public TrueOrFalseQuestion(int question_id, int exam_id, String question_text, int point_value){
		super(question_id, question_text, point_value, exam_id); 
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
		return b.toString();
	}

	@Override
	public boolean isManuallyGraded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}

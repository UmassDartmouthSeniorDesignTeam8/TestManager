
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
		super(question_text, point_value); //is this needed because I couldn't get it working.... 
		this.question_id = question_id;
		this.exam_id = exam_id;
		this.question_text = question_text; //but it works with these lines added in so... 
		this.point_value = point_value;
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

	@Override
	public boolean isManuallyGraded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}

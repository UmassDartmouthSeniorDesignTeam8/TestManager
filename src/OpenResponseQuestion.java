
public class OpenResponseQuestion extends Question {


	private static final long serialVersionUID = -29356089960775464L;

	private int question_id;
	private int exam_id;
	private String question_text;
	private int answer_id;
	private int question_type; //don't think it's needed in here. 
	private int point_value;
	
	
	
	public OpenResponseQuestion(int question_id, int exam_id, String question_text, int point_value){
		super(question_text, point_value); //is this needed because I couldn't get it working.... 
		this.question_id = question_id;
		this.exam_id = exam_id;
		this.question_text = question_text; //but it works with these lines added in so... 
		this.point_value = point_value;
	}


	@Override
	public boolean isManuallyGraded() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

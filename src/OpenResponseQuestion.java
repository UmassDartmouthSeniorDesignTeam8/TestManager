
public class OpenResponseQuestion extends Question {


	private static final long serialVersionUID = -29356089960775464L;

	
	
	
	public OpenResponseQuestion(int question_id, int exam_id, String question_text, int point_value){
		super(question_id, question_text, point_value, exam_id); 
	}


	@Override
	public boolean isManuallyGraded() {
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

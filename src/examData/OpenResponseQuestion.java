package examData;


public class OpenResponseQuestion extends Question {


	private static final long serialVersionUID = -29356089960775464L;

	
	
	//no longer any question_id and exam_id <co>
//	public OpenResponseQuestion(int question_id, int exam_id, String question_text, int point_value){
//		super(question_id, question_text, point_value, exam_id); 
//	}

	public OpenResponseQuestion(String question_text, int point_value){
		super(question_text, point_value); 
	}
	
	public int getNumChoices(){
		return 0;
	}


	@Override
	public boolean isManuallyGraded() {
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

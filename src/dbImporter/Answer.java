package dbImporter;
import java.io.Serializable;


public class Answer implements Serializable  {

	private static final long serialVersionUID = -8272622275712458557L;
	private int question_id;
	private int response_id;	
	private String response_text;
	
	public Answer(int question_id, String response_text, int response_id){
		this.question_id = question_id;
		this.response_text = response_text;
		this.response_id = response_id;
	}
	
	public void createAnswerKey(int test_id){

		/*will call getAnswers(test_id); I think unless we just use the DatabaseImporter which is also possible and I'm just adding more work. */
	}
	
	
	//debugging purposes: probably going to be deleted. 
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Answer: question_id= " );
		b.append(question_id);
		b.append(" response_text = >");
		b.append(response_text);
		b.append("< response_id = ");
		b.append(response_id);
		return b.toString();
	}
	
	
	
	
	
}

public class OpenEndedQuestion extends Question {
	
	private static final long serialVersionUID = 8114689309779362118L;
	private String sampleResponse;
	
	public OpenEndedQuestion(String questionText, int pointValue, String sampleResponse){
		super(questionText, pointValue);
		this.sampleResponse = sampleResponse;
	}

	@Override
	public boolean isManuallyGraded() {
		return true;
	}
	
	public String getSampleResponse(){
		return sampleResponse;
	}

}

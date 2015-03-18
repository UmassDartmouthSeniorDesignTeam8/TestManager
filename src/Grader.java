import java.util.ArrayList;


public class Grader {
	private Exam exam;
	private ArrayList<Response> responses;
	private ManualGradeHandler handler;
	
	/**
	 * The grader class associates a list of responses with the exam itself.
	 * This will be used to generate the actual grades and manually handled
	 * items, such as open response questions.
	 * @param e
	 * @param r
	 * @param h
	 */
	public Grader(Exam e, ArrayList<Response> r, ManualGradeHandler h){
		this.exam = e;
		this.responses = r;
		this.handler = handler;
	}
	
	public void generateGrades(){
		// first declare a jagged array to hold the possible responses for all questions [student][question][response]
		boolean[][][] qrCodesFound = new boolean[exam.getCourse().getNumStudents()][exam.getNumQuestions()][];
		Question questions[] = exam.getQuestionArray();
		// now declare the appropriate size for each question in the array; by default all booleans will be false
		for (int q=0; q<exam.getNumQuestions(); q++){
			for (int s=0; s<exam.getCourse().getNumStudents(); s++){
				if (questions[q].isManuallyGraded())
						qrCodesFound[s][q]= new boolean[0];
				else
						qrCodesFound[s][q] = new boolean[questions[q].getNumChoices()];					
			}
		}
		//now we go through all the questions and fill them into the array where necessary
		for (Response r: responses){
			if (r.getResponseNum()==QRCodeHandler.RESPONSE_MEANS_OPEN_RESPONSE)
				handler.addNewOpenResponseItem(r.getStudentID(), r.getQuestionNum(), questions[r.getQuestionNum()], r.getCoordinates());
		}
				
	}
}

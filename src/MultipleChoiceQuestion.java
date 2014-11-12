import java.util.ArrayList;

import com.lowagie.text.Paragraph;


public class MultipleChoiceQuestion extends Question {
	
	private static final long serialVersionUID = -29356089960775464L;
	public final int MAX_RESPONSES = 10;
	private String choices[];
	private int correctChoice;
	
	/**
	 * 
	 * @param questionText		The text of the question.
	 * @param pointValue		The number of points awarded for a correct answer.
	 * @param choices			An array of strings of possible choices.
	 * @param correctChoice		Index of the correct answer within the choices array.
	 */
	public MultipleChoiceQuestion(String questionText, int pointValue,
			String choices[], int correctChoice){
		super(questionText, pointValue);
		if (choices.length > MAX_RESPONSES||correctChoice>=choices.length)
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
	
	public String[] getChoices(){
		return choices;
	}

	@Override
	public boolean isManuallyGraded() {
		return false;
	}
	
	public Paragraph[] generateFormattedPDFSection(){
		ArrayList<Paragraph> ps = new ArrayList<Paragraph>();
		ps.add(new Paragraph(this.getQuestionText() + " (" + this.getPointValue() + " points)"));
		for (int i=0; i<choices.length; i++){
			Paragraph current = new Paragraph((char)('A'+i) + ". " + choices[1]);
			current.setIndentationLeft(50);
			current.setSpacingBefore(0);
			current.setSpacingAfter(0);
			ps.add(current);
		}
		Paragraph[] paragraphs = new Paragraph[ps.size()];
		for (int i=0; i<paragraphs.length; i++)
			paragraphs[i]=ps.get(i);
		return paragraphs;
	}

}

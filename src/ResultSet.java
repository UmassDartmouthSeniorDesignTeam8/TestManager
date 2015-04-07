import java.util.ArrayList;


public class ResultSet {
	// The following four arrays are associative.
	String[] students;			// retains student names
	char chosenResponses[][];	// holds MC response for [student][question], 0 for unknown
	int pointsAwarded[][];		// holds points given for each [student][question]
	int pointsPossible[];		// num points possible per question
	public final int numStudents;
	public final int numQuestions;
	
	Exam exam;
	Question questions[];					// holds the exam
	
	// Constructor initializes arrays
	public ResultSet(Exam e){
		numStudents = e.getCourse().getNumStudents();
		numQuestions = e.getNumQuestions();
		this.exam = e;
		this.questions = exam.getQuestionArray();
		chosenResponses = new char[numStudents][numQuestions];
		students = new String[numStudents];
		pointsAwarded = new int[numStudents][numQuestions];
		for (int i=0; i<questions.length; i++){
			pointsPossible[i] =  questions[i].getPointValue();
		}
	}
	
	/**	Provides a guaranteed multiple choice result for a question. Overrides existing values.
	 * 
	 * @param student		student number
	 * @param question		question number
	 * @param result		multiple choice response index (A = 0)
	 * @return				true if result recorded successfully	 */
	public boolean provideMCResult(int student, int question, int result){
		if (questions[question] instanceof MultipleChoiceQuestion&&student<students.length&&question<questions.length){
			chosenResponses[student][question] = (char)('A' + result);
			pointsAwarded[student][question] = ((MultipleChoiceQuestion) questions[question]).gradeResponse(result);
			return true;
		}
		return false;
	}
	
	/** Provices a point value for a given question. Overrides existing value.
	 * 
	 * @param student		student number
	 * @param question		question number
	 * @param points		number of points awarded (will accept any value)
	 * @return				True if grade accepted. False is not accepted (question not manually graded)
	 */
	public boolean provideManuallyGradedResult(int student, int question, int points){
		if (questions[question].isManuallyGraded()&&student<students.length&&question<questions.length){
			pointsAwarded[student][question]  = points;
			return true;
		}
		return false;
	}
	
	/**
	 * Provides a student's name to associate with a student number.
	 * 
	 * @param student		student number
	 * @param name			question number
	 * @return				True if name accepted. False if not accepted (name already exists or name provided is null)
	 */
	public boolean provideStudentName(int student, String name){
		if (student<students.length&&students[student]==null&&name!=null){
			students[student] = name;
			return true;
		}
		return false;
	}
	
	/**
	 * Provides a student's name.
	 * @param student		student number
	 * @return				Student name if provided; Student number otherwise
	 */
	public String getStudentName(int student){
		if (student<students.length){
			if (students[student]==null)
				return "Student #" + student;
			else
				return students[student];
		}
		return "Invalid Student #" + student;
	}
}

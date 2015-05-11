package htmlGrader;
import java.io.PrintWriter;
import java.util.ArrayList;

import examData.Exam;
import examData.MultipleChoiceQuestion;
import examData.Question;


public class ResultSet {
	// The following four arrays are associative.
	String[] students;			// retains student names
	char chosenResponses[][];	// holds MC response for [student][question], 0 for unknown
	int pointsAwarded[][];		// holds points given for each [student][question]
	int pointsPossible[];		// num points possible per question
	int totalPoints = 0;  		// total number of points a test can have
	private final int numStudents;
	private final int numQuestions;
	private boolean manualComplete, autoComplete;
	
	private Exam exam;
	Question questions[];					// holds the exam
	
	// Constructor initializes arrays
	public ResultSet(Exam e){
		numStudents = e.getNumPrinted();
		numQuestions = e.getNumQuestions();
		this.exam = e;
		this.questions = exam.getQuestionArray();
		chosenResponses = new char[numStudents][numQuestions];
		students = new String[numStudents];
		pointsPossible = new int[exam.getNumQuestions()];
		pointsAwarded = new int[numStudents][numQuestions];
		for (int i=0; i<questions.length; i++){
			if (questions[i]==null)
				System.out.println("Question " + i + " is empty.");
			else{
				pointsPossible[i] =  questions[i].getPointValue();
				totalPoints += pointsPossible[i];
			}
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
	
	public boolean providePoints(int student, int question, int points){
		pointsAwarded[student][question] = points;
		return true;
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
	
	public void notifyAllAutomaticGradingComplete(){
		autoComplete = true;
	}
	
	public void notifyAllManualGradingComplete(){
		manualComplete = true;
	}
	
	public boolean isReady(){
		return (manualComplete && autoComplete);
	}
	
	public void writePointsCSV(String fileName) {
		PrintWriter output = null;

		try {
			output = new PrintWriter(fileName);
			// Write the CSV file header
			output.print("name");
			for(int i = 0; i < numQuestions; i++){
				output.print(",Question["+ i + "] pts[" + pointsPossible[i] + "]");
			}
			output.println(", Total Points["+ totalPoints +"]");
			/*
			 * In theory this will add a student and then for that student it
			 * will go through their test and mark down the number of points
			 * awarded for each question; keeping a running total for the
			 * students grade then in the 1st loop it appends that and then adds
			 * an end line marker
			 */
			for (int stu = 0; stu < numStudents; stu++) {
				int total = 0;
				output.print(getStudentName(stu));
				for (int quest = 0; quest < numQuestions; quest++) {
					output.print("," + (char) pointsAwarded[stu][quest]);
					total += pointsAwarded[stu][quest];
				}
				// will put in the students total
				output.println(","+(char) total);
			}

		} catch (Exception e) {
			System.out.println("Error in CsvPrintWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				output.flush();
				output.close();
			} catch (Exception e) {
				System.out
						.println("Error while flushing/closing PrintWriter !!!");
				e.printStackTrace();
			}

		}
	}

	public void writeAnswerCSV(String fileName) {

		PrintWriter output = null;

		try {
			output = new PrintWriter(fileName);
			
			// Write the CSV file header
			output.println("name");
			for (int i = 0; i < numQuestions; i++) {			
				if (!questions[i].isManuallyGraded()){
					output.print(",Question["+ i + "] ans[" + ((MultipleChoiceQuestion) questions[i]).getCorrectChoice() + "]");
				}
			}
			output.println();
			
			/*
			 * In theory this will add a student and then for that student it
			 * will go through their test and mark down what their answer was
			 *  then adds an end line marker
			 */
			for (int stu = 0; stu < numStudents; stu++) {
				output.print(getStudentName(stu));
				for (int quest = 0; quest < numQuestions; quest++) {
					if (!questions[quest].isManuallyGraded()){
						output.print(","+chosenResponses[stu][quest]);	
					}
				}
				// adding in the end line marker
				output.println();
			}

		} catch (Exception e) {
			System.out.println("Error in CsvPrintWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				output.flush();
				output.close();
			} catch (Exception e) {
				System.out
						.println("Error while flushing/closing PrintWriter !!!");
				e.printStackTrace();
			}

		}
	}
	
	
	
}

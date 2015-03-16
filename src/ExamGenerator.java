import java.util.Date;


public class ExamGenerator {
	public static Exam getExam(){
		Course course = new Course("Sample Course", "Testy McTesterson", "Spring 2015");
		course.addStudent(new Student("Alyssa Tavares",""));
		course.addStudent(new Student("Chris Cobb",""));
		course.addStudent(new Student("Caitlyn O'Shaughnessy",""));
		course.addStudent(new Student("Shawn Beaudoin",""));
		
		String[] responses = {"1","2","3","4","5"};
		MultipleChoiceQuestion question1 = new MultipleChoiceQuestion("Select 4.",5,responses,3);
		MultipleChoiceQuestion question2 = new MultipleChoiceQuestion("Select 2.",5,responses,1);
		MultipleChoiceQuestion question3 = new MultipleChoiceQuestion("The answer is 5, idiot.",5,responses,4);
		Exam e = new Exam("Practice Exam", new Date(), "", course);
		e.addQuestion(question1);
		e.addQuestion(question2);
		e.addQuestion(question3);
		return e;
	}
}

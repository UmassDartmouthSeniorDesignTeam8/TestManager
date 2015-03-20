import java.util.ArrayList;
import java.util.Date;


public class ExamGenerator {
	public static Exam getExam(){
		Course course = new Course("Sample Course", "Testy McTesterson", "Spring 2015");
		course.addStudent(new Student("Alyssa Tavares",""));
		course.addStudent(new Student("Chris Cobb",""));
		course.addStudent(new Student("Caitlyn O'Shaughnessy",""));
		course.addStudent(new Student("Shawn Beaudoin",""));
		
		/*I really think an array list will be easier when working with the db <co>*/
		ArrayList<String> responses = new ArrayList<String>(){{
			   add("1");
			   add("2");
			   add("3");
			   add("4");
			   add("5");
			   }};

			   
		//NOW the parameters are (question_text, point_value, r, answer_id) <co>
		MultipleChoiceQuestion question1 = new MultipleChoiceQuestion("Select 4.",5,responses,3);
		MultipleChoiceQuestion question2 = new MultipleChoiceQuestion("Select 2.",5,responses,1);
		System.out.println(question1.getNumChoices());
		System.out.println(question2.getNumChoices());
		MultipleChoiceQuestion question3 = new MultipleChoiceQuestion("The answer is 5, idiot.",5,responses,4);
		/*constructor has to match... I don't know why it isn't liking the test name. <co>*/
		//Exam e = new Exam("Practice Test1", 1);
		// This constructor is declared; otherwise the date is never specified. <SB>
		Exam e = new Exam("Practice Exam", new Date(), "", course);
		e.addQuestion(question1);
		e.addQuestion(question2);
		e.addQuestion(question3);
		return e;
	}
}

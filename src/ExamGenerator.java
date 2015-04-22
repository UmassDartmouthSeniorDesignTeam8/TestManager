import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class ExamGenerator {
	public static Exam getExam(){
		Course course = new Course("CIS 180", "Midterm Exam", "Spring 2015");
		course.addStudent(new Student("Alyssa Tavares",""));
		course.addStudent(new Student("Chris Cobb",""));
		course.addStudent(new Student("Caitlyn O'Shaughnessy",""));
		course.addStudent(new Student("Shawn Beaudoin",""));
		
		/*I really think an array list will be easier when working with the db <co>*/
		ArrayList<String> responses = new ArrayList<String>(){{
			   add("int");
			   add("double");
			   add("char");
			   add("ArrayList");
			   add("float");
			   add("boolean");
			   add("byte");
			   }};

			   
		//NOW the parameters are (question_text, point_value, r, answer_id) <co>
		MultipleChoiceQuestion question1 = new MultipleChoiceQuestion("Which of the following is not a primitive data type in Java?",5,responses,3);
		ArrayList<String> responses2 = new ArrayList<String>(){{
				add("Very High Speed Integrated Circuit Hardware Description Langugage");
				add("Virtual Hot Dog Lovers");
				add("Vast Hardware Description Language");
				add("Very High Downloads");
		}};
			
		MultipleChoiceQuestion question2 = new MultipleChoiceQuestion("What does VHDL stand for?",5,responses2,0);		
		
		ArrayList<String> responses3 = new ArrayList<String>(){{
				add("2.4");
				add("2");
				add("3");
				add("Undefined");
		}};
		
		MultipleChoiceQuestion question3 = new MultipleChoiceQuestion("What is the value of x after the following statement?<br>int x = 12/5;",10, responses3, 2);
		/*constructor has to match... I don't know why it isn't liking the test name. <co>*/
		//Exam e = new Exam("Practice Test1", 1);
		// This constructor is declared; otherwise the date is never specified. <SB>
		Exam e = new Exam("Practice Exam", new Date(), "", course, 12345);
		e.addQuestion(question1);
		e.addQuestion(question2);
		e.addQuestion(question3);
		return e;
	}
	
	public static Exam commandLine(){
		String question, response;
		Scanner input = new Scanner(System.in);
		Course crs = new Course("Sample Course", "Instructor Name", "Spring 2015");
		crs.addStudent(new Student("Student A"));
		crs.addStudent(new Student("Student B"));
		crs.addStudent(new Student("Student C"));
		crs.addStudent(new Student("Student D"));
		crs.addStudent(new Student("Student E"));
		Exam e = new Exam("Sample Exam", new Date(), "", crs, (int)Math.random()*30427);
		do{
			System.out.print("Enter question text or . to exit: ");
			question = input.nextLine();
			ArrayList<String> responses = new ArrayList<String>();
			int correct = 0;
			if (!question.equals(".")){
				responses = new ArrayList<String>();
				int count = 0;
				do{
					System.out.print("Enter response " + (count) + ": ");
					response = input.nextLine();
					if (!response.equals(".")){
						responses.add(response);
					}
				} while (!response.equals(".")&&++count<10);
				System.out.print("Correct response #: ");
				correct = input.nextInt();
				input.nextLine();
				MultipleChoiceQuestion q = new MultipleChoiceQuestion(question, 5, responses, correct);
				e.addQuestion(q);
			}			
		} while (!question.equals("."));
		return e;
	}
	
	public static void main(String args[]){
		Exam e = new ExamGenerator().getExam();
		e.setNumberPrinted(10);
		System.out.println(e);
		HTMLGenerator gen = new HTMLGenerator(e);
		gen.generateSingleHTML("C:\\Orion\\cmdSample", true, 10);
		FileOutputStream fout = null;
		try {
			File parent = new File("C:\\Orion\\exams\\");
			parent.mkdirs();
			fout = new FileOutputStream("C:\\Orion\\exams\\e"+e.getExamID());
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(e);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
		} finally {
			try {
				fout.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}

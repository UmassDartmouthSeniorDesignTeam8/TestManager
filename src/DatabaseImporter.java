import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseImporter {

	/*
	 * for the database, which I didn't its name so I guessed and named it
	 * OrionDB
	 */

	private static Connection conn;
	
	//gets connected to the database
	private static void getDatabaseConnection() {
			String url = "jdbc:mysql://50.87.248.81:3306/"; 
			String dbName = "cobbsoft_orion"; 
			String driver = "com.mysql.jdbc.Driver"; 
			String userName = "cobbsoft"; 
			String password = "Infinite=12345"; 
		
			try { 
				
				Class.forName(driver); 
				conn = DriverManager.getConnection(url+dbName,userName,password);
				
				} catch (Exception e) { 
					System.out.println("ERROR CONNECTING!");
					e.printStackTrace(); 
				} 
			
	} 
	
	/*goes through and gets the questions*/
	public static Collection<Question> getQuestions(int test_id) throws SQLException {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select question_id, test_id, question_text, question_type, point_value from questions  where test_id = '"
						+ test_id + "'");
		
		//read in from the database
		while (resultSet.next()) {
			System.out.println("setting info from the db");
			int question_id = resultSet.getInt(1);
			int exam_id = resultSet.getInt(2);	
			String question_text = resultSet.getString(3);
			int question_type = resultSet.getInt(4);
			int point_value = resultSet.getInt(5);
			//add a question
			
			if(question_type == 0){
				Question q = new MultipleChoiceQuestion(question_id, exam_id, question_text, point_value);
				questions.add(q);
				System.out.println(q.toString()); //here for debugging
				
				System.out.println("added a multiple choice question"); // testing
			}
/*			else if(question_type == 3){
				questions.add(new TrueOrFalseQuestion());
			}
			else{
				questions.add(new OpenResponseQuestion());
			}
*/		}

		return questions;
	}

/*I do not believe this will be needed*/
/*	public static Exam getExam(int class_id, int test_id) throws SQLException {

		//getDatabaseConnection();
		Statement stmt = conn.createStatement();
		lines 104-106 were commented out
		ResultSet resultSet = stmt
				.executeQuery("select exam_name, course_id , coverpageins, date from test where test_id = '"
						+ test_id + "' and class-id = '" + class_id + "'");
		
		ResultSet resultSet = stmt.executeQuery("select test_name, test_id, from tests where test_id = '"
						+ test_id + "'");
		String exam_name = resultSet.getString(1);
		int exam_id = resultSet.getInt(2);
		//int course_id = resultSet.getInt(1);
		//String coverpageins = resultSet.getString(2);
		//Date date = resultSet.getDate(3);

		//Exam ret = new Exam(exam_name, date, coverpageins, course_id);
		Exam ret = new Exam(exam_name, exam_id);
		for (Question q : Exam.getQuestionArray(test_id)) {
		ret.addQuestion(q);
		}
		
		return null;
	} */

	public static ArrayList<Answer> getAnswers(int test_id) throws SQLException{
		ArrayList<Answer> answers = new ArrayList<Answer>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		//when is_answer is set to 1 that response will be added. 
		ResultSet resultSet = stmt.executeQuery("SELECT r.question_id, response_text, answer_id FROM responses r, questions q WHERE  q.test_id = '"+ test_id+"' AND r.question_id = q.question_id AND r.is_answer = 1");
		
		//read in from the database
		try {
			while (resultSet.next()) {
				System.out.println("setting info from the db");
				int question_id = resultSet.getInt(1); 
				String response_text = resultSet.getString(2);			
				int response_id = resultSet.getInt(3);
				Answer a = new Answer(question_id, response_text, response_id);
				answers.add(a);
				System.out.println(a.toString()); //here for debugging
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB");
			e.printStackTrace();
		}

		return answers;
		
		
		
	}
	
	
	public static void main(String args[]) throws SQLException {

		DatabaseImporter di= new DatabaseImporter();
		
		System.out.println("calling get getAnswers");

		ArrayList<Answer> answers = di.getAnswers(1);
		for (Answer a : answers) {
			System.out.println("Returned: " + a.toString());
		}
		
		Collection<Question> questions = di.getQuestions(1);
		
	}
	
}	
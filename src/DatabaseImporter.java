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

//	public final static String DB_URL = "http://cobbsoftwaresystems.com/cobbsoft_orion";
//	public final static String USER = "cobbsoft";
//	public final static String PASS = "Infinite=12345";
//	
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
//				
//		try {
//			
//			System.out.println("getting connection"); //debugging
//			
//			String sDriverName = "com.mysql.jdbc.Driver";
//			Class.forName(sDriverName);
//
//			System.out.println("gets to conn"); //debugging
//			
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			
//			System.out.println("Connection succsefull"); //debugging
//		}
//
//		catch (Exception ex) {
//			ex.printStackTrace();
//			System.exit(0);
//		}
	
	/*goes through and gets the questions*/
	public static Collection<Question> getQuestions(int test_id) throws SQLException {
		
		System.out.println("in getQuestions"); //debugging
		
		
		ArrayList<Question> questions = new ArrayList<Question>();
		
		System.out.println("getting connection"); //debugging
		
		getDatabaseConnection();
		
		System.out.println("got connection; creating stmt");
		
		Statement stmt = conn.createStatement();
		
		System.out.println("about to query"); //debugging		
		
		ResultSet resultSet = stmt.executeQuery("select question_id, test_id, question_text, answer_id, question_type, point_value from questions  where test_id = '"
						+ test_id + "'");
		
		System.out.println("did the query");
		//read in from the database
		while (resultSet.next()) {
			System.out.println("setting info from the db");
			int question_id = resultSet.getInt(1);
			int testId = resultSet.getInt(2);			//duplicate variable if names test_id
			String question_text = resultSet.getString(3);
			int answer_id = resultSet.getInt(4);
			String question_type = resultSet.getString(5);
			int point_value = resultSet.getInt(6);
			//add a question
			
			System.out.println("showing the info");
			
			System.out.println(question_id);
			System.out.println(testId);
			System.out.println(question_text);
			System.out.println(question_type);
			System.out.println(point_value);
			
		//questions.add(new Question(quest_id, question_text, answer, pointValue));
		}

		return questions;
	}

	/*goes and gets entire exams*/
	public static Exam getExam(int class_id, int test_id) throws SQLException {

		//getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("select exam_name, course_id , coverpageins, date from test where test_id = '"
						+ test_id + "' and class-id = '" + class_id + "'");
		String exam_name = resultSet.getString(0);
		int course_id = resultSet.getInt(1);
		String coverpageins = resultSet.getString(2);
		Date date = resultSet.getDate(3);

		//Exam ret = new Exam(exam_name, date, coverpageins, course_id);
		//for (Question q : Question.getQuestions(test_id)) {
		//	ret.addQuestion(q);
		//}
		
		return null;
	}

	
	public static void main(String args[]) throws SQLException {

		DatabaseImporter di= new DatabaseImporter();
		
		System.out.println("calling get questions");

		di.getQuestions(1);

	}
	
}	
	
	
	
	
	
	
	
	
	
	


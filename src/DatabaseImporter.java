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

		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select quest_id, question_text, answer, pointValue from questions  where test-id = '"
						+ test_id + "'");
		//read in from the database
		while (resultSet.next()) {
			int quest_id = resultSet.getInt(0);
			String question_text = resultSet.getString(1);
			String answer = resultSet.getString(2);
			int pointValue = resultSet.getInt(3);
			//add a question
			System.out.println(quest_id);
			System.out.println(question_text);
			System.out.println(answer);
			System.out.println(pointValue);

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

		di.getQuestions(0);

	}
	
}	
	
	
	
	
	
	
	
	
	
	


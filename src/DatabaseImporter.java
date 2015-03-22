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

	private static Connection conn;
	
	//gets connected to the database
	private static void getDatabaseConnection() {
			if(conn == null){
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
	} 
	
	/*goes through and gets the questions, for a given test_id.... <co>*/
	public static Collection<Question> getQuestions(int test_id) throws SQLException {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT question_id, test_id, question_text, question_type, point_value FROM questions WHERE test_id = '"+ test_id + "'");
	
		//read in from the database
		while (resultSet.next()) {
			System.out.println("getting info from the db"); //debugging
			int question_id = resultSet.getInt(1);
			int exam_id = resultSet.getInt(2);	
			String question_text = resultSet.getString(3);
			int question_type = resultSet.getInt(4);
			int point_value = resultSet.getInt(5);
			//add a question
			
			if(question_type == 0){
				System.out.println("Getting Responses for Question: "+ question_id);
				ArrayList<String> r =  getResponses(exam_id, question_id);
				int cc = getCorrectChoice(exam_id, question_id);
				/*MultipleChoiceQuestion now ONLY takes question_text, point_value, an array list of responses and an answer_id
				 * That being said is answer_id the position of the correct answer? <co> */
				System.out.println("READ BACK AS: "+r);
				Question q = new MultipleChoiceQuestion(question_text, point_value, r, cc); //causes issue after 4 questions because of the non-exsistant way of telling the actual correct choice
				questions.add(q);
				System.out.println(q.toString()); //here for debugging
				
				System.out.println("added a multiple choice question"); // testing
			}
/*			else if(question_type == 3){
				Question q = new TrueOrFalseQuestion(question_id, exam_id, question_text, point_value);
				questions.add(q);
				System.out.println(q.toString()); //here for debugging
				
				System.out.println("added a true/false question"); // testing
			}
			else{
				Question q = new OpenResponseQuestion(question_id, exam_id, question_text, point_value);
				questions.add(q);
				System.out.println(q.toString()); //here for debugging
				
				System.out.println("added a open response question"); // testing
				
				
				
			}
*/		}

		return questions;
	}
	
	/*Is called by getQuestions to get the responses for the mc questions only <co>*/
	public static ArrayList<String> getResponses(int exam_id, int question_id) throws SQLException{
		ArrayList<String> responses = new ArrayList<String>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT response_text FROM responses WHERE question_id = '"
						+ question_id + "' AND test_id = '"+ exam_id + "'");
		
		//read in from the database
		while (resultSet.next()) {
			//System.out.println("setting info from the db"); //debugging
			String response_text = resultSet.getString(1);
			System.out.println("RESPONSE: "+ response_text); // testing

			responses.add(response_text);
		}
		
		System.out.println("RESPONSE: "+ responses); // testing
		return responses;
	}
	
	/*Called in getQuestions:
	 * Gets the id of the response id of the correct answer <co>*/
	public static int getCorrectChoice(int exam_id, int question_id) throws SQLException{
		int response_id = 0;
		String response_text;
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		 
		ResultSet resultSet = stmt.executeQuery("SELECT response_id, response_text FROM questions q, responses r WHERE q.test_id = '"+ exam_id +"' AND q.question_id ='"+question_id+"' AND  r.question_id = q.question_id AND r.test_id = q.test_id AND r.is_answer = 1");
		try {
			while (resultSet.next()) {
				response_id = resultSet.getInt(1);
				response_text = resultSet.getString(2);
				System.out.println("Answer:"+ response_text +" -----AnswerID: "+ response_id);
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: in getCorrectChoice");
			e.printStackTrace();
		}
		return response_id;
	
	}
	
	
	
	/*Hypothetically will give the correct response (ie Answer Key)
	 * When a response is_answer is equal to 1, then that get added to the an arraylist of 
	 * answers <co>*/
	public static ArrayList<Answer> getAnswers(int test_id) throws SQLException{
		ArrayList<Answer> answers = new ArrayList<Answer>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		//when is_answer is set to 1 that response will be added. 
		ResultSet resultSet = stmt.executeQuery("SELECT r.question_id, response_text, answer_id FROM responses r, questions q WHERE  q.test_id = '"+ test_id+"' AND r.question_id = q.question_id AND r.is_answer = 1");
		
		//read in from the database
		try {
			while (resultSet.next()) {
				//System.out.println("setting info from the db");
				int question_id = resultSet.getInt(1); 
				String response_text = resultSet.getString(2);			
				int response_id = resultSet.getInt(3);
				Answer a = new Answer(question_id, response_text, response_id);
				answers.add(a);
				System.out.println(a.toString()); //here for debugging
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: getAnswers");
			e.printStackTrace();
		}

		return answers;
		
		
		
	}
	
	/*Used in AdminGUI to see if a ID is in the db <co>*/
	public static boolean isAdminID(int givenID) throws SQLException{
		int admin_id = -1;
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		 
		ResultSet resultSet = stmt.executeQuery("SELECT admin_id FROM admin WHERE  admin_id = '"+ givenID+"'");
		
		try {
			while (resultSet.next()) {
				admin_id = resultSet.getInt(1); 
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: isAdminID");
			e.printStackTrace();
		}

		boolean isAdmin = admin_id != -1;
		return isAdmin;
	}
	
	/*used in ClassesGUI: to get the list of classes <co>*/
	public static ArrayList<String> getClasses(int admin_id) throws SQLException{
		
		ArrayList<String> classes = new ArrayList<String>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		 
		ResultSet resultSet = stmt.executeQuery("SELECT c_name FROM classes c, admin a WHERE  a.admin_id = '"+ admin_id+"' AND c.admin_id ='" +admin_id +"' AND c.is_active = 1");
		
		try {
			while (resultSet.next()) {
				String c = resultSet.getString(1);			
				classes.add(c);
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: in getClasses");
			e.printStackTrace();
		}
		return classes;
	}
	
	/*Used in TestGUI: to get list of tests <co>*/
	public static ArrayList<String> getTestNames(String class_name) throws SQLException{
		
		ArrayList<String> test_names = new ArrayList<String>();
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		 
		ResultSet resultSet = stmt.executeQuery("SELECT test_name FROM tests t, classes c WHERE c.c_name = '"+ class_name+"' AND  t.class_id = c.class_id AND t.is_active = 1");
		
		try {
			while (resultSet.next()) {
				String t = resultSet.getString(1);			
				test_names.add(t);
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: in getTestNames");
			e.printStackTrace();
		}
		return test_names;
	}
	
	/*used in TestGUI: to call getQuestions <co>*/
	public static int getTestID(String test_name) throws SQLException{
		int test_id = 0;
		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		 
		ResultSet resultSet = stmt.executeQuery("SELECT test_id FROM tests  WHERE test_name = '"+ test_name+"'");
		
		try {
			while (resultSet.next()) {
				test_id = resultSet.getInt(1);			
			}
		} catch (SQLException e) {

			System.out.println("Problem saving from DB: in getTestNames");
			e.printStackTrace();
		}
		return test_id;
		
	}
	
	
	
/* public static void main(String args[]) throws SQLException {

		DatabaseImporter di= new DatabaseImporter();
		
		System.out.println("calling get getAnswers");

		ArrayList<Answer> answers = di.getAnswers(1);
		for (Answer a : answers) {
			System.out.println("Returned: " + a.toString());
		}
		
		Collection<Question> questions = di.getQuestions(1); //testing
		
	} */
	
}	
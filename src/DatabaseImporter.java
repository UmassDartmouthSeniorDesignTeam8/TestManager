import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class DatabaseImporter {

	/*
	 * for the database, which I didn't its name so I guessed and named it
	 * OrionDB
	 */
	public final String DB_URL = "jdbc:derby:OrionDB.derby";
	private Connection conn;
	
	//gets connected to the database
	private void getDatabaseConnection() {
		try {
			String sDriverName = "org.derby.JDBC";
			Class.forName(sDriverName);

			conn = DriverManager.getConnection(DB_URL);
		}

		catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}

	}
	
	/*goes through and gets the questions*/
	public static Collection<Question> getQuestions(int test_id) {
		ArrayList<Question> questions = new ArrayList<Question>();

		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("select quest_id, question_text, answer, pointValue from questions  where test-id = '"
						+ test_id + "'");
		//read in from the database
		while (resultSet.next()) {
			int quest_id = resultSet.getInt(0);
			String question_text = resultSet.getString(1);
			String answer = resultSet.getString(2);
			int pointValue = resultSet.getInt(3);
			//add a question
			questions.add(new Question(quest_id, question_text, answer,
					pointValue));
		}

		return questions;
	}

	/*goes and gets entire exams*/
	public static Exam getExam(int class_id, int test_id) {

		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("select exam_name, course_id , coverpageins, date from test where test_id = '"
						+ test_id + "' and class-id = '" + class_id + "'");
		String exam_name = resultSet.getString(0);
		int course_id = resultSet.getInt(1);
		String coverpageins = resultSet.getString(2);
		Date date = resultSet.getDate(3);

		Exam ret = new Exam(exam_name, date, coverpageins, course_id);
		for (Question q : Question.getQuestions(test_id)) {
			ret.addQuestion(q);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

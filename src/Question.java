import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Paragraph;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String questionText;
	private int pointValue;

	/*
	 * for the database, which I didn't its name so I guessed and named it
	 * OrionDB
	 */
	public final String DB_URL = "jdbc:derby:OrionDB.derby";
	private Connection conn;
	private int quest_id;
	private String answer;

	public Question(int quest_id, String questionText, String answer,
			int pointValue) {
		super();
		this.quest_id = quest_id;
		this.questionText = questionText;
		this.answer = answer;
		this.pointValue = pointValue;

	}

	public abstract boolean isManuallyGraded();

	protected abstract void generateFormattedPDFSection();

	public abstract Paragraph[] getFormattedPDFSection();

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public static Collection<Question> getQuestions(int test_id) {
		ArrayList<Question> questions = new ArrayList<Question>();

		getDatabaseConnection();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("select quest_id, question_text, answer, pointValue from questions  where test-id = '"
						+ test_id + "'");
		while (resultSet.next()) {
			int quest_id = resultSet.getInt(0);
			String question_text = resultSet.getString(1);
			String answer = resultSet.getString(2);
			int pointValue = resultSet.getInt(3);
			questions.add(new Question(quest_id, question_text, answer,
					pointValue));
		}

		return questions;
	}

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

	/* code taken from QRCodeHandler... */
	public void setQuestionQRCode(String imagepath, String contents, int size) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix image = writer.encode(contents, BarcodeFormat.QR_CODE,
					size, size);

			MatrixToImageWriter.writeToPath(image, "GIF", Paths.get(imagepath));

		} catch (Exception e) {
			System.out.println("Error occured while generating QR-Code");
		}
	}
}

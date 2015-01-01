import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

public class Exam implements Serializable {

	private static final long serialVersionUID = 3897617004954994453L;
	private String name;
	private Date date;
	private String coverPageInstructions;
	private Course course;
	private ArrayList<Question> questions;
	private int index = 0;
	private static final int SIZE = 25;

	/*
	 * for the database, which I didn't its name so I guessed and named it
	 * OrionDB
	 */
	public final String DB_URL = "jdbc:derby:OrionDB.derby";
	private Connection conn;

	public Exam(String name, Date date, String coverPageInstructions,
			int course_id) {
		super();
		this.name = name;
		this.date = date;
		this.coverPageInstructions = coverPageInstructions;
		this.course_id = course_id;
		questions = new ArrayList<Question>();
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public String getCoverPageInstructions() {
		return coverPageInstructions;
	}

	public Course getCourse() {
		return course;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCoverPageInstructions(String coverPageInstructions) {
		this.coverPageInstructions = coverPageInstructions;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

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
	
	public void addQuestion(Question q) {

		questions.add(q);
		/* to get the index of the question */
		String in = Integer.toString(questions.size() - 1);
		/* should set the qr code for the question */
		q.setQuestionQRCode(this.getName(), in, SIZE);

	}

	public void generateExamDocument(String filepath) {
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(filepath));

			HeaderFooter header = new HeaderFooter(new Phrase(course.getName()
					+ " - " + this.name + " - " + course.getTerm()), false);
			header.setAlignment(Element.ALIGN_CENTER);

			document.setHeader(header);

			document.open();

			for (Question q : questions) {
				Paragraph[] toAdd = q.getFormattedPDFSection();
				for (Paragraph p : toAdd) {
					document.add(p);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Unable to create pdf: " + e.getMessage());
		} finally {
			document.close();
		}
	}
}

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

public class Exam implements Serializable {

	private static final long serialVersionUID = 3897617004954994453L;
	private String name;
	private Date date;
	private String coverPageInstructions;
	private Course course;
	private ArrayList<Question> questions;
	private int examID;
	
	public Exam(String name, Date date, String coverPageInstructions,
			Course course) {
		super();
		this.name = name;
		this.date = date;
		this.coverPageInstructions = coverPageInstructions;
		this.course = course;
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
	
	public int getExamID(){
		return examID;
	}
	
	public Question[] getQuestionArray(){
		return (Question[])questions.toArray();
	}
	
	public int getNumQuestions(){
		return questions.size();
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

	public void addQuestion(Question q) {
		questions.add(q);
		/* to get the index of the question */
		String in = Integer.toString(questions.size() - 1);
		/* should set the qr code for the question */
		//q.setQuestionQRCode(this.getName(), in, SIZE);

	}

//	public void generateExamDocument(String filepath) {
//		Document document = new Document();
//
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream(filepath));
//
//			HeaderFooter header = new HeaderFooter(new Phrase(course.getName()
//					+ " - " + this.name + " - " + course.getTerm()), false);
//			header.setAlignment(Element.ALIGN_CENTER);
//
//			document.setHeader(header);
//
//			document.open();
//
//			for (Question q : questions) {
//				Paragraph[] toAdd = q.getFormattedPDFSection();
//				for (Paragraph p : toAdd) {
//					document.add(p);
//				}
//			}
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null,
//					"Unable to create pdf: " + e.getMessage());
//		} finally {
//			document.close();
//		}
//	}
}

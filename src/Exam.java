import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;


public class Exam implements Serializable{
	
	private static final long serialVersionUID = 3897617004954994453L;
	private String name;
	private Date date;
	private String coverPageInstructions;
	private Course course;
	private ArrayList<Question> questions;
	
	public Exam(String name, Date date, String coverPageInstructions, Course course) {
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
	
	public Course getCourse(){
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
	
	public void setCourse(Course course){
		this.course = course;
	}
	
	public void addQuestion(Question q){
		questions.add(q);
	}
	
	public void generateExamDocument(String filepath){
		Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream(filepath));            
            
            //HeaderFooter header = new HeaderFooter(new Phrase(course.getName() + " - " + name + " - " + " - " + 
            //	course.getTerm()), false);
            HeaderFooter header = new HeaderFooter(new Phrase("TEXT"), false);
            header.setAlignment(Element.ALIGN_CENTER);
            System.out.println(header);
            
            document.setHeader(header);
            
            document.open();
            
            for (Question q: questions){
            	Paragraph[] toAdd = q.generateFormattedPDFSection();
            	for (Paragraph p:toAdd){
            		document.add(p);   
            	}
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
        	JOptionPane.showMessageDialog(null, "Unable to create pdf: " + e.getMessage());
        }finally {
        	document.close();
        }
    }
}


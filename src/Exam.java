import java.io.Serializable;
import java.util.Date;


public class Exam implements Serializable{
	
	private static final long serialVersionUID = 3897617004954994453L;
	private String name;
	private Date date;
	private String coverPageInstructions;
	private Course course;
	
	public Exam(String name, Date date, String coverPageInstructions, Course course) {
		super();
		this.name = name;
		this.date = date;
		this.coverPageInstructions = coverPageInstructions;
		this.course = course;
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
}

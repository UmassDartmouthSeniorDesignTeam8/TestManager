import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{
	
	private static final long serialVersionUID = 5240685614928147611L;
	private String name, instructor, term;
	private ArrayList<Student> students;
	
	public Course(String name, String instructor, String term) {
		this.name = name;
		this.instructor = instructor;
		this.term = term;
		students = new ArrayList<Student>();
	}

	public String getName() {
		return name;
	}

	public String getInstructor() {
		return instructor;
	}

	public String getTerm() {
		return term;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	public boolean addStudent(Student student){
		if (students.contains(student))
			return false;
		students.add(student);
		return true;
	}
	
	/**
	 * 
	 * @param student	Student to be removed from course.
	 * @return			True if removal is successful; false otherwise.
	 */
	public boolean removeStudent(Student student){
		if (students.contains(student)){
			students.remove(student);
			return true;
		}
		return false;
	}
}

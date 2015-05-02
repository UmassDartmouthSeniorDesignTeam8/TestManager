package examData;
import java.io.Serializable;


public class Student implements Serializable{
	
	private static final long serialVersionUID = 6011316924467691033L;
	private String name, email;

	public Student(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Student(String name) {
		this.name = name;
		email = "none";
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

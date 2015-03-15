/* 
 * Alyssa Tavares
 * RESPONSE CLASS USED TO HOLD:
 * version number, exam ID, student ID, question number, and answer number
 * also holds the page number that response is on
 */
public class Response 
{
	private String versionNum;
	private String examID;
	private String studentID;
	private String questionNum;
	private String answerNum;
	private int pageNum;

	public Response(String v, String e, String s, String q, String a, int p)
	{
		this.versionNum = v;
		this.examID = e;
		this.studentID = s;
		this.questionNum = q;
		this.answerNum = a;
		this.pageNum = p;
	}

	// SETTERS
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}


	public void setExamID(String examID) {
		this.examID = examID;
	}


	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}


	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}


	public void setAnswerNum(String answerNum) {
		this.answerNum = answerNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	// GETTERS
	public String getVersionNum() {
		return versionNum;
	}


	public String getExamID() {
		return examID;
	}


	public String getStudentID() {
		return studentID;
	}


	public String getQuestionNum() {
		return questionNum;
	}


	public String getAnswerNum() {
		return answerNum;
	}


	public int getPageNum() {
		return pageNum;
	}

}
import com.google.zxing.ResultPoint;

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
	// I renamed AnswerNum to REsponseNum for consistent naming; this is most likely because I wrote it down incorrectly originally <SB>
	private String responseNum;
	private int pageNum;
	private ResultPoint[] coordinates;
	
	public Response(String v, String e, String s, String q, String a, int p, ResultPoint[] c)
	{
		this.versionNum = v;
		this.examID = e;
		this.studentID = s;
		this.questionNum = q;
		this.responseNum = a;
		this.pageNum = p;
		this.coordinates = c;
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


	public void setResponseNum(String answerNum) {
		this.responseNum = answerNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public void setCoordinates(ResultPoint[] c)
	{
		this.coordinates = c;
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


	public String getResponseNum() {
		return responseNum;
	}


	public int getPageNum() {
		return pageNum;
	}
	
	public ResultPoint[] getCoordinates()
	{
		return coordinates;
	}

}
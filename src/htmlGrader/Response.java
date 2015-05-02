package htmlGrader;
import java.util.Arrays;

import com.google.zxing.ResultPoint;

/* 
 * Alyssa Tavares
 * RESPONSE CLASS USED TO HOLD:
 * version number, exam ID, student ID, question number, and answer number
 * also holds the page number that response is on
 */
public class Response 
{
	public final int OPEN_RESPONSE = -1;
	public final int STUDENT_IDENTIFIER = -2;
	
	private int versionNum;
	private int examID;
	private int studentID;
	private int questionNum;
	private int answerNum;
	private int pageNum;
	private ResultPoint[] coordinates;
	
	public Response(int v, int e, int s, int q, int a, int p, ResultPoint[] c)
	{
		this.versionNum = v;
		this.examID = e;
		this.studentID = s;
		this.questionNum = q;
		this.answerNum = a;
		this.pageNum = p;
		this.coordinates = c;
	}

	// SETTERS
	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}


	public void setExamID(int examID) {
		this.examID = examID;
	}


	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}


	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}


	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public void setCoordinates(ResultPoint[] c)
	{
		this.coordinates = c;
	}

	// GETTERS
	public int getVersionNum() {
		return versionNum;
	}


	public int getExamID() {
		return examID;
	}


	public int getStudentID() {
		return studentID;
	}


	public int getQuestionNum() {
		return questionNum;
	}


	public int getAnswerNum() {
		return answerNum;
	}


	public int getPageNum() {
		return pageNum;
	}
	
	public ResultPoint[] getCoordinates()
	{
		return coordinates;
	}

	@Override
	public String toString() {
		return "Response [versionNum=" + versionNum + ", examID=" + examID
				+ ", studentID=" + studentID + ", questionNum=" + questionNum
				+ ", answerNum=" + answerNum + ", pageNum=" + pageNum
				+ ", coordinates=" + Arrays.toString(coordinates) + "]";
	}
	
	public boolean isOpenResponse(){
		if (answerNum==-1)
			return true;
		return false;
	}
	
	public boolean isStudentIdentifier(){
		if (questionNum<0||answerNum==-2)
			return true;
		return false;
	}

}
package htmlGenerator;
import misc.QRCodeHandler;
import examData.Exam;


public class HTMLGeneratorJS extends HTMLGenerator {
	
	public HTMLGeneratorJS(Exam e){
		super(e);
	}
	
	// doesn't generate the QR codes
	public void generateHTML(String fileDirectory, boolean isRandomized){
		this.fileDirectory = fileDirectory;
		// Randomize if needed; otherwise stock the questions array with the normal ordering
		this.generateQuestionOrderings(isRandomized);
		for (int s=0; s<exam.getCourse().getNumStudents(); s++){
			System.out.println("Generating exam " + s);
			generateSingleCopyOfExam(s, fileDirectory + "\\s" + s + ".html");
		}
	}
	
	protected String getPageHTMLHeader(){
		return "<html>\n	<head>\n<link rel =\"stylesheet\" type = \"text/css\" href=\"basic.css\"><title>" +
				exam.getName() +  " - " + exam.getCourse().getName() +
				"</title>\n	<script type=\"text/javascript\" src=\"jquery.min.js\"></script>" +
				"<script type=\"text/javascript\" src=\"qrcode.js\"></script></head>\n <body>\n";
	}
	
	protected String getQRCodeHTML(int student, int questionNum, int response){
		String contents = QRCodeHandler.getQRCodeContents(1, exam.getExamID(), student, questionNum, response);
		return "<div class = \"responseImageFrame\"><div id = \"" + contents + "\" class = \"qrcode\"></div></div>\n" +
				"<script type = \"text/javascript\">new QRCode(document.getElementById(\"" + contents + "\"), {text: \"" + contents + "\", width: 32, height: 32});" +
				"</script>";
	}
	

}

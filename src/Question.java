import java.io.Serializable;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String questionText;
	private int pointValue;

	private int quest_id;
	private String answer;

	public Question(String questionText, int pointValue) {
		this.questionText = questionText;
		this.pointValue = pointValue;
	}

	public abstract boolean isManuallyGraded();

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

//	/* code taken from QRCodeHandler... */
//	public void setQuestionQRCode(String imagepath, String contents, int size) {
//		try {
//			QRCodeWriter writer = new QRCodeWriter();
//			BitMatrix image = writer.encode(contents, BarcodeFormat.QR_CODE,
//					size, size);
//
//			MatrixToImageWriter.writeToPath(image, "GIF", Paths.get(imagepath));
//
//		} catch (Exception e) {
//			System.out.println("Error occured while generating QR-Code");
//		}
//	}
}

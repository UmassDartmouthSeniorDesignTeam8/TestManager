import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Paragraph;

public abstract class Question implements Serializable {

	private static final long serialVersionUID = -8272622275712458557L;
	private String questionText;
	private int pointValue;

	private int quest_id;
	private String answer;

	public Question(String questionText, int pointValue) {
		super();
		this.quest_id = quest_id;
		this.questionText = questionText;
		this.answer = answer;
		this.pointValue = pointValue;

	}

	public abstract boolean isManuallyGraded();

	protected abstract void generateFormattedPDFSection();

	public abstract Paragraph[] getFormattedPDFSection();

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

	/* code taken from QRCodeHandler... */
	public void setQuestionQRCode(String imagepath, String contents, int size) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix image = writer.encode(contents, BarcodeFormat.QR_CODE,
					size, size);

			MatrixToImageWriter.writeToPath(image, "GIF", Paths.get(imagepath));

		} catch (Exception e) {
			System.out.println("Error occured while generating QR-Code");
		}
	}
}

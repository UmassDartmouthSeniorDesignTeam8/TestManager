import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;


public class QRCodeHandler 
{
	protected static ArrayList<ResultPoint[]> points = new ArrayList<ResultPoint[]>();
	
	public static String[] readAllCodes(BufferedImage exam) throws NotFoundException
	{
		QRCodeMultiReader reader = new QRCodeMultiReader();
		String[] resultStrings;

		LuminanceSource source = new BufferedImageLuminanceSource(exam); // what exactly is a luminance source?
		BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));

		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);

		Result[] results = reader.decodeMultiple(bitmap, hints);

		resultStrings = new String[results.length];
		int i=0;
		for (Result r: results)
		{
			points.add(r.getResultPoints());
			resultStrings[i++] = r.getText();
		}
		return resultStrings; 
	}
	
	/* ADDED TO GET THE RESULT POINTS ARRAY */
	public static ArrayList<ResultPoint[]> getCoordinates() throws NotFoundException
	{
		return points;
	}


	public static boolean GenerateQRCode(String imagepath, String contents, int size){
		try{
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix image = writer.encode(contents, BarcodeFormat.QR_CODE, size, size);

			MatrixToImageWriter.writeToPath(image, "GIF", Paths.get(imagepath));
			return true;
		} catch (Exception e){
			return false;
		} 		
	}

	public static boolean GenerateBarCode(String imagepath, String contents, int size){
		try{
			Code128Writer writer = new Code128Writer();
			BitMatrix image = writer.encode(contents, BarcodeFormat.CODE_128, 50, 25);

			MatrixToImageWriter.writeToPath(image, "GIF", Paths.get(imagepath));
			return true;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return false;
		} 		
	}
}

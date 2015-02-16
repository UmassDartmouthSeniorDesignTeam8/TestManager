import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
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


public class QRCodeHandler {
	
	public static String[] readAllCodes(BufferedImage image) throws NotFoundException{
		QRCodeMultiReader reader = new QRCodeMultiReader();
		String[] resultStrings;
		
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
		
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);
        
        Result[] results = reader.decodeMultiple(bitmap, hints);
        
        resultStrings = new String[results.length];
        int i=0;
        for (Result r: results){
        	System.out.print("Barcode  " + (i+1));
        	ResultPoint[] points = r.getResultPoints();
        	for (ResultPoint p: points)
        		System.out.println("("+p.getX() + ", " + p.getY() +")");
        	resultStrings[i++] = r.getText();
        }
        
        return resultStrings;        
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
	
	/*
	 * Generates a unique QR code that fully encodes all aspects of a multiple choice response.
	 */
	public static boolean generateQRCode(String version, int exam, int student, int question,
			int response, String filePath, int size){
		String toEncode = "v" + version + "e" + exam + "s" + student + "q" + question + "r" + response;
		return GenerateQRCode(filePath, toEncode, size);
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

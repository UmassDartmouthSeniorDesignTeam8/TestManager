import java.awt.image.BufferedImage;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;


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
        	resultStrings[i++] = r.getText();
        }
        
        return resultStrings;        
	}
}

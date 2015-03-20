import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
 

public class errorDisplay {
	
	 
	/**
     * Reads in pdf writes shapes to PDF
     * 
     * @param src  the path to the original PDF file
     * @param dest the path to the resulting text file
     * @throws IOException
	 * @throws DocumentException 
     */
    public void parsePdf(String src, String dest, int x1, int y1, int x2, int y2) throws IOException, DocumentException {
    	
    	//create doc 
    	Document document = new Document();
    	
    	//create a writer for adding shapes
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
    	
    	//open doc
    	document.open();
    	
    	//get writer content
    	PdfContentByte cb = writer.getDirectContent();
    	
    	//create a reader stream pointing to src
    	PdfReader reader = new PdfReader(src);
    	document.setPageSize(reader.getPageSize(1));

    	// Add new data, aka notifications or boxes
    	cb.rectangle(x1,y1,x2,y2);
    	cb.stroke(); 

    	//close streams
    	reader.close();
    	document.close();
    }
 
    
    /**
     * Main method test calls to write rectangles.
     * 
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args) throws DocumentException, IOException {
    	errorDisplay example = new errorDisplay();
    	//errorDisplay.main(args);
        
    	//this is a random shape created at these points, showing drawing comment out in production
    	example.parsePdf("mock.pdf", "mock.pdf", 200, 200, 200, 200);
    }

}

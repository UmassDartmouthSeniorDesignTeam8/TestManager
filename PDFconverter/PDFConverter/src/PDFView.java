/* Should view a PDF */
import java.io.File;
import com.sun.pdfview.PDFViewer;

public class PDFView 
{
	private static File pdf; // pdf this class works with 
	
	public PDFView(File pdf)
	{
		this.pdf = pdf;
	}
	protected void execute() throws Exception
	{
		// display it on a JFrame
		PDFViewer pdfv = new PDFViewer(true);
		pdfv.openFile(pdf) ;
		pdfv.setEnabling();
		pdfv.pack();
		pdfv.setVisible(true);
	}

	public static void main(String[] args) 
	{
		PDFView main = new PDFView(pdf);
		try 
		{
			main.execute();
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}

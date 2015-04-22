/* Alyssa Tavares
 * Converts a PDF to a JPEG */

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
 
public class PDFtoImage
{ 
	private static BufferedImage[] bufferedImages;
	// converted to static so it can be called without making an object <sb>
    public static BufferedImage[] convert(File pdf) throws Exception 
    {
        //  load a pdf from a file
        RandomAccessFile raf = new RandomAccessFile(pdf, "r");
        ReadableByteChannel ch = Channels.newChannel(new FileInputStream(pdf));
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,
                0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
 
        // get number of pages
        int numPages = pdffile.getNumPages();
        
        // array of images for the exam
        // each piece of the array is an image of one page of the exam
    	bufferedImages = new BufferedImage[numPages];
 
        // iterate through the number of pages
        for (int i = 0; i <= numPages-1; i++) 
        {
            PDFPage page = pdffile.getPage(i);
 
            // create a new image
            Rectangle rect = new Rectangle(0, 0,
                    (int) page.getBBox().getWidth(),
                    (int) page.getBBox().getHeight());
            
            /* Parameters for Image object:
             * rect.width = the width
             * rect.height = the height
             * rect = clip rectangle
             * true = fill background with white
             * true = block until drawing is done
             */
            Image img = page.getImage(rect.width, rect.height, rect, null, true, true);
 
            bufferedImages[i] = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImages[i].createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            
        }
        raf.close();
		return bufferedImages;
    }

 
//    public static void main(String[] args) 
//    {
//        PDFtoImage converter = new PDFtoImage(); // create converter
//        
//        File exam = new File("exam.pdf"); // pdf file you want to convert
//        try {
//            converter.convert(exam); // call to converter method and pass pdf file
//        } catch (Exception ex) 
//        {
//            ex.printStackTrace();
//        }
//        
//        // display JPEG (not necessary just wanted to make sure it works)
////        ImageViewer viewer = new ImageViewer();
////        for(int i = 0; i < bufferedImages.length; i++) {
////        	viewer.displayImage(bufferedImages[i]);
////        }
//    }
}
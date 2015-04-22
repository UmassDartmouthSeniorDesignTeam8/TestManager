import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;


public class GhostRenderer {
	
	public static ArrayList<BufferedImage> getImages(String filepath){
		File source = new File(filepath);
			
		PDFDocument document = new PDFDocument();
		try {
			document.load(source);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SimpleRenderer renderer = new SimpleRenderer();
		
		renderer.setResolution(125);
		
		List<Image> images;
		try {
			try {
				images = renderer.render(document);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} catch (RendererException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ArrayList<BufferedImage> buffered = new ArrayList<BufferedImage>();
		for (Image i: images){
			BufferedImage toadd = new BufferedImage(i.getWidth(null),i.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = toadd.createGraphics();
			g.drawImage(i,0,0,null);
			g.dispose();
			buffered.add(toadd);
		}
		
		return buffered;
	}
	public static void main(String[] args) throws Exception{
	
		File source = new File("C:\\Orion\\sampleExam.pdf");
		
		PDFDocument document = new PDFDocument();
		document.load(source);
		
		SimpleRenderer renderer = new SimpleRenderer();
		
		renderer.setResolution(150);
		
		List<Image> images = renderer.render(document);
		ArrayList<BufferedImage> buffered = new ArrayList<BufferedImage>();
		for (Image i: images){
			BufferedImage toadd = new BufferedImage(i.getWidth(null),i.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = toadd.createGraphics();
			g.drawImage(i,0,0,null);
			g.dispose();
			buffered.add(toadd);
		}
		
		for (BufferedImage i: buffered){
			ImageViewer view = new ImageViewer();
			view.displayImage(i);
		}
		
		ArrayList<Response> responses = QRStringInterpreter.interpret(buffered);
		
		for (Response r: responses){
			System.out.println(r);
		}
		
	}
}
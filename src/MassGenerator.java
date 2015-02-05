import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class MassGenerator {
	
	public static void main(String args[]) throws FileNotFoundException{
	
//		for (int i=0; i<100; i++){
//			QRCodeHandler.GenerateBarCode(i + ".gif", "21456" + i + "", 58);
//		}
	//	testCreation("");
	testLoad("58pxwrite.bmp");

	}
	
	private static void testCreation(String filepath){
		PrintWriter output;
		try {
			output = new PrintWriter("output.html");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		output.println("<html><body><table>");
		for (int i=0; i<100; i++){
			if (i%6==0){
				output.println("<tr><td colspan=2>All right. So here is a question and some shit. Yeah I'm fucking awesome.</td></tr>");
			}
			output.println("<tr><td><img src =\"" + i + ".gif\"></img> </td><td>Here is question number " + i + " .</td></tr>");
//			if (i%6==0)
//				output.println("</tr><tr>");
//			output.println(/*"<td>" + i + ".*/"<td> <img src =\"" + i + ".gif\"></img> </td>");
		}
		output.println("</table></body></html>");
		output.close();
		System.out.println("Done");
	
	}
	
	private static void testLoad(String filepath){
		try{
			BufferedImage img = ImageIO.read(new File(filepath));
			String[] found = QRCodeHandler.readAllCodes(img);
			Arrays.sort(found);
			for (String s: found)
				System.out.println("Found " + s);
		} catch (Exception e){
		}
	}
}

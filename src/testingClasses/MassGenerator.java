package testingClasses;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;

import misc.QRCodeHandler;


public class MassGenerator {
	
	public static void main(String args[]) throws FileNotFoundException{
//		long startime = System.currentTimeMillis();
//		int i = 0;
//		for (int student =0; student<100; student++)
//			for (int q=0; q<100; q++)
//					for (int r=0; r<5; r++)
//							QRCodeHandler.generateQRCode("1", 215, student, q, r, 58);
//	
//		long endTime = System.currentTimeMillis();
//		System.out.println(endTime-startime);
		
			
		//testCreation("");
	testLoad("40test.bmp");

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
		int i=0;
		output.println("<html><body><table>");
		for (int student =0; student<5; student++)
			for (int q=0; q<6; q++){
					output.println("<tr><td colspan=2> Here is Question " + q + " for Student " + student + "</td><tr>");
					for (int r=0; r<5; r++)
						output.println("<tr><td><img src=\"" + i++ + ".gif\"></img> </td><td>Response " + r + " .</td></tr>");
			}
						
//		for (int i=0; i<150; i++){
//			if (i%6==0){
//				output.println("<tr><td colspan=2>All right. So here is a question and some shit. Yeah I'm fucking awesome.</td></tr>");
//			}
//			output.println("<tr><td><img src =\"" + i + ".gif\"></img> </td><td>Here is question number " + i + " .</td></tr>");
////			if (i%6==0)
////				output.println("</tr><tr>");
////			output.println(/*"<td>" + i + ".*/"<td> <img src =\"" + i + ".gif\"></img> </td>");
//		}
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

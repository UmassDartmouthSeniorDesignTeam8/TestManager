import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MassGenerator {
	
	public static void main(String args[]) throws FileNotFoundException{
	
		for (int i=0; i<100; i++){
			QRCodeHandler.GenerateQRCode(i + ".gif", i + "", 25);
		}
		
		PrintWriter output = new PrintWriter("output.html");
		output.println("<html><body><table>");
		for (int i=0; i<100; i++){
			if (i%6==0)
				output.println("</tr><tr>");
			output.println(/*"<td>" + i + ".*/"<td> <img src =\"" + i + ".gif\"></img> </td>");
		}
		output.println("</table></body></html>");
		output.close();
	}
}

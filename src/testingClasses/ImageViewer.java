package testingClasses;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageViewer
{
	JFrame frame;
	
	public void displayImages(BufferedImage imgs)
	{
		{
			frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(imgs)));
			frame.pack();
			frame.setVisible(true);
		}
	}
}

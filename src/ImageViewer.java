import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageViewer
{
	JFrame frame;
	
	protected void displayImages(BufferedImage[] imgs)
	{
		for(int i = 0; i < imgs.length; i++)
		{
			frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(imgs[i])));
			frame.pack();
			frame.setVisible(true);
		}

	}
}

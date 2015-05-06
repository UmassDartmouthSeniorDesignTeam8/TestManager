package htmlGrader;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ManualGrader.java
 * 
 * A class responsible for the manual user interaction on exceptions found in QR Decoding.
 * These errors can be caused by multiple markings or unexpected marks on QR Code. The manual
 * grader is also responsible for the user to define the student who had taken the test. 
 *
 */
public class ManualGrader extends JFrame{
	public final static int NO_RESPONSE = -4000;

	private JPanel contentPane;
	private BufferedImage localImage;
	
	//manual grader window for showing exceptions
	public ManualGrader(BufferedImage img, int num_answers, RectangleBoundary rb) {
		
		//create frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		contentPane = new JPanel();
		
		//get boundary points from RectangleBoundary Object
		int topLeftX = (int)rb.getTopLeftX();
		int topLeftY = (int)rb.getTopLeftY();
		int bottomRightX = (int)rb.getBottomRightX(); 
		int bottomRightY = (int)rb.getBottomRightY();
		
		// Draw rectangle in image
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke((float) 3.0));
		System.out.println(img.getColorModel());
		System.out.println(img.getHeight() + " " + img.getWidth());
		System.out.println(topLeftX + " " + topLeftY + " " + bottomRightX + " " + bottomRightY);
		g2d.drawRect(topLeftX, topLeftY, bottomRightX-topLeftX, bottomRightY-topLeftY);
		g2d.dispose();
		
		
		//dropdown possibilities
		String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H","I", "J" };
		
		//create dropdown
		JComboBox comboBox = new JComboBox(labels);
		
		//only show possibilities
	    comboBox.setMaximumRowCount(num_answers);
	    
	    //user can't edit
	    comboBox.setEditable(false);
	    
	    //grade button
	    JButton btn = new JButton("Grade It!");
	    
	    //set content bounds
	    contentPane.setBounds(0, 0, 600, 600);
	    
	    //set combobox bounds
	    comboBox.setBounds(50, 50, 450, 200);
	    
	    //set combobox bounds
	    btn.setBounds(50, 50, 450, 200);
	    
	    //add image
	    JLabel image_frame = new JLabel(new ImageIcon(img));
	    
	    //set combobox bounds
	    btn.setBounds(50, 250, 450, 200);
	    
	    //add to frame
	    contentPane.add(comboBox);
	    contentPane.add(btn);
	    contentPane.add(image_frame);
	    setContentPane(contentPane);  
	    
	    this.setVisible(true);
		
	}
	
	public void setErrorMessage(String message){
		
	}

}

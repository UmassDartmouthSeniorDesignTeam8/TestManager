package htmlGrader;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

/**
 * ManualGrader.java
 * 
 * A class responsible for the manual user interaction on exceptions found in QR Decoding.
 * These errors can be caused by multiple markings or unexpected marks on QR Code. The manual
 * grader is also responsible for the user to define the student who had taken the test. 
 *
 */
public class ManualGraderGUI extends JFrame implements ActionListener{
	public final static double POINTS_NO_RESPONSE = -4000.1;
	public final static int MULTIPLE_CHOICE_NO_RESPONSE = -2;

	private BufferedImage localImage;
	private JComboBox<String> responseLetters;
	private JComboBox<String> studentNames;
	private JTextField points;
	private JLabel warningLabel;
	private JButton submit;
	private ManualGradeHandler handler;
	private Dimension dim;
	private JPanel center;
	
	//manual grader window for showing exceptions
	public ManualGraderGUI(ManualGradeHandler handler, String[] studentNames) {
		this.handler = handler;
		this.setLayout(new BorderLayout());
		
		//create frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);	
		
		this.studentNames = new JComboBox<String>(studentNames);
		this.studentNames.setEditable(true);
		this.studentNames.setSize(100, (int)this.studentNames.getSize().getHeight());
		
		this.points = new JTextField();
		points.setSize(100, (int)points.getSize().getHeight());
		
		
		/*//dropdown possibilities
		String labels[] = { "", "Unknown", "A", "B", "C", "D", "E", "F", "G", "H","I", "J" };
		
		//create dropdown
		responseLetters = new JComboBox<String>(labels);
	    
	    //user can't edit
	    responseLetters.setEditable(false);*/
	    
	    //grade button
	    submit = new JButton("Submit");
	    this.getRootPane().setDefaultButton(submit);
	    submit.addActionListener(this);
	    
	    //create label for error messages on the bottom
	    warningLabel = new JLabel();
	    warningLabel.setForeground(Color.RED);
	    warningLabel.setSize((int)dim.getWidth()-100, (int)warningLabel.getPreferredSize().getHeight());
	    
	    responseLetters = new JComboBox<String>();
	    buildChoiceComboBox(0);
	    responseLetters.setSize(100, (int)responseLetters.getPreferredSize().getHeight());
	    
	    //set content bounds
	    //contentPane.setBounds(0, 0, 600, 600);
	    
	    //set combobox bounds
	    //comboBox.setBounds(50, 50, 450, 200);
	    
	    //set combobox bounds
	    //btn.setBounds(50, 50, 450, 200);
	    
	    /*JScrollPane image_frame = new JScrollPane(new JLabel(new ImageIcon(img)));
	    image_frame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    image_frame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    image_frame.setPreferredSize(new Dimension(dim.width-50, dim.height-100));
	    JPanel center = new JPanel(new FlowLayout());
	    center.add(image_frame);*/
	    
	    //set combobox bounds
	    //btn.setBounds(50, 250, 450, 200);
	    
	    //add to frame
	    JPanel topmenu = new JPanel(new FlowLayout());
	    topmenu.add(responseLetters);
	    topmenu.add(this.studentNames);
	    topmenu.add(points);
	    topmenu.add(submit);
	    
	    //this.add(center, BorderLayout.CENTER);
	    this.add(topmenu, BorderLayout.NORTH);	
	}
	
	public void setErrorMessage(String message){
		warningLabel.setText(message);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		double numPoints;
		String studName;
		int choice;
		if (ev.getSource() == submit){
			try{
				numPoints = Double.parseDouble(points.getText());
			} catch (Exception e){
				numPoints = POINTS_NO_RESPONSE;
			}
			if (studentNames==null||studentNames.getSelectedItem()==null)
				studName = null;
			else
				studName = (String)studentNames.getSelectedItem();
			if (!responseLetters.isEnabled())
				choice = MULTIPLE_CHOICE_NO_RESPONSE;
			else
				choice = responseLetters.getSelectedIndex() + -1;
			//System.out.println(numPoints + " " + choice + " " + studName);
			handler.getResponseFromGUI(numPoints, choice, studName);
		}		
	}
	
	public void displayItem(BufferedImage image, RectangleBoundary boundary, boolean requiresMultipleChoice, boolean requiresPoints,
			boolean requiresStudentName, int numChoices){
		drawImage(image, boundary);
		if (requiresMultipleChoice){
			buildChoiceComboBox(numChoices);
			responseLetters.setEnabled(true);
			responseLetters.requestFocus();
		} else{
			buildChoiceComboBox(0);
			responseLetters.setSelectedIndex(0);
			responseLetters.setEnabled(false);
		}
		if (requiresPoints){
			points.setEnabled(true);
			points.requestFocus();
		} else {
			points.setEnabled(false);
			points.setText(null);
		}
		if (requiresStudentName){
			studentNames.setEnabled(true);
			studentNames.requestFocus();
		} else {
			studentNames.setEnabled(false);
			studentNames.setSelectedIndex(-1);
		}
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	private void drawImage(BufferedImage image, RectangleBoundary rb){
		// first clone the image so that the original isn't drawn on
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
		localImage =  new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		
		/*localImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		Graphics2D g = localImage.createGraphics();
		g.drawImage(image, 0, 0, null);*/
		
		//get boundary points from RectangleBoundary Object
		int topLeftX = (int)rb.getTopLeftX();
		int topLeftY = (int)rb.getTopLeftY();
		int bottomRightX = (int)rb.getBottomRightX(); 
		int bottomRightY = (int)rb.getBottomRightY();
		
		// Draw rectangle in image
		//localImage = image;
		Graphics2D g = localImage.createGraphics();
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke((float) 3.0));
		g.drawRect(topLeftX, topLeftY, bottomRightX-topLeftX, bottomRightY-topLeftY);
		g.dispose();
		
		JScrollPane image_frame = new JScrollPane(new JLabel(new ImageIcon(localImage)));
	    image_frame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    image_frame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    image_frame.setPreferredSize(new Dimension(dim.width-50, dim.height-150));
	    if (center==null){
	    	center = new JPanel(new FlowLayout());
	    	center.add(image_frame);
	    	this.add(center, BorderLayout.CENTER);
	    } else{
	    	center.removeAll();
	    	center.add(image_frame);
	    	center.revalidate();
	    	center.repaint();
	    }
	}
	
	private void buildChoiceComboBox(int numChoices){
		responseLetters.removeAllItems();
		if (numChoices<0)
			numChoices = 0;
		/*String[] list = new String[numChoices+2];
		list[0] = " ";
		list[1] = "Unknown";
		for (int i=0; i<numChoices; i++){
			list[2+i] = (char)('A' + i) + "";
		}*/
		responseLetters.addItem("Unknown");
		for (int i=0; i<numChoices; i++)
			responseLetters.addItem((char)('A' + i) + "");
		//responseLetters.setModel(list);
	}
	
	public void close(){
		this.dispose();
	}

}

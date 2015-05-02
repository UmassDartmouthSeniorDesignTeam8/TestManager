package htmlGrader;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class rectDraw extends JPanel {
	
	int x;
	int y;
	int width;
	int height;
	
	public rectDraw(int x1, int y1, int height1, int width1){
		
		//define rect bounds
		x = x1;
		y = y1;
		height = height1;
		width = width1;
	
	}
	
	//draw rectangle with bounds
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);  
	    g.drawRect(x,y,width,height);  
	    g.setColor(Color.RED);    
	  }
	}
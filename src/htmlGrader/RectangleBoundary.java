package htmlGrader;
import com.google.zxing.ResultPoint;
/*
 * This class holds a bounding rectangle used to highlight specific questions/CR codes in the document.
 * It can be automatically generated from a collection of points. RectangleBoundary may not span more than
 * a single page or it will not function correctly.
 */

public class RectangleBoundary {
	private float topLeftX, topLeftY, bottomRightX, bottomRightY;
	private int pageNumber;
	
	// Create a new one with only the page number
	public RectangleBoundary(int pageNum){
		this.pageNumber = pageNum;
		topLeftX = Integer.MAX_VALUE;
		topLeftY = Integer.MAX_VALUE;
		bottomRightX = 0;
		bottomRightY = 0;
	}
	
	// Create one when all points are known as well as the page number
	public RectangleBoundary(ResultPoint[] points, int pageNum){
		this(pageNum);
		// determine the farthest points for Xs and Ys
		addPoints(points);
	}
	
	// Expands the boundary to include more points when a new QR code is found
	public void addPoints(ResultPoint[] points){
		for (ResultPoint p: points){
			if (p.getX() < topLeftX)
				topLeftX = p.getX();
			if (p.getX() > bottomRightX)
				bottomRightX = p.getX();
			if (p.getY() < topLeftY)
				topLeftY = p.getY();
			else if (p.getY() > bottomRightY)
				bottomRightY = p.getY();
		}
	}

	public float getTopLeftX() {
		return topLeftX;
	}

	public float getTopLeftY() {
		return topLeftY;
	}

	public float getBottomRightX() {
		return bottomRightX;
	}

	public float getBottomRightY() {
		return bottomRightY;
	}
	
	public int getPageNum(){
		return pageNumber;
	}
	
	public void setPageNum(int pageNumber){
		this.pageNumber = pageNumber;
	}
	
	public void addHeight(float height){
		bottomRightX+=height;
		bottomRightY+=height;
	}
}

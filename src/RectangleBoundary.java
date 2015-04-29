import com.google.zxing.ResultPoint;
/*
 * This class holds a bounding rectangle used to highlight answer selections in the PDF document.
 * It can be automatically generated from a collection of points.
 */

public class RectangleBoundary {
	private float topLeftX, topLeftY, bottomRightX, bottomRightY;
	private int pageNumber;
	
	public RectangleBoundary(int pageNum){
		this.pageNumber = pageNum;
		topLeftX = 0;
		topLeftY = 0;
		bottomRightX = Integer.MAX_VALUE;
		bottomRightY = Integer.MAX_VALUE;
	}
	
	public RectangleBoundary(ResultPoint[] points, int pageNum){
		this(pageNum);
		// determine the farthest points for Xs and Ys
		addPoints(points);
	}
	
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

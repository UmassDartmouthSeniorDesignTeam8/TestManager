import com.google.zxing.ResultPoint;
/*
 * This class holds a bounding rectangle used to highlight answer selections in the PDF document.
 * It can be automatically generated from a collection of points.
 */

public class RectangleBoundary {
	private float topLeftX, topLeftY, bottomRightX, bottomRightY;
	
	public RectangleBoundary(ResultPoint[] points){
		float maxX = 0, maxY = 0, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		// determine the farthest points for Xs and Ys
		for (ResultPoint p: points){
			if (p.getX() < minX)
				minX = p.getX();
			else if (p.getX() > maxX)
				maxX = p.getX();
			if (p.getY() < minY)
				minY = p.getY();
			else if (p.getY() > maxY)
				maxY = p.getY();
		}
		topLeftX = minX;
		topLeftY = minY;
		bottomRightX = maxX;
		bottomRightY = maxY;
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
}

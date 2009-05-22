package components;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public interface ISpatialObject2D {
	/**
	 * Add a point to the component
	 * @param point
	 */
	public void addPoint(Point point);
	
	/**
	 * 
	 * @return List with points
	 */
	public List<Point> getPoints();
	
	/**
	 * 
	 * @return component bound
	 */
	public Rectangle getBound();
}

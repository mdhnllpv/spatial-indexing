package components;

import java.awt.Graphics;
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
	 * Paint the component
	 * @param g - Graphics
	 */
	public void paintComponent(Graphics g);
	
	/**
	 * 
	 * @return component bound
	 */
	public Rectangle getBound();
}

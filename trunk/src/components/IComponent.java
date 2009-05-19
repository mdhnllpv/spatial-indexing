package components;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public interface IComponent {
	/**
	 * 
	 * @return object bound
	 */
	public Rectangle getBound();
	
	/**
	 * 
	 * @return Return list with point of the object
	 */
	public List<Point> getPoints();
	
	/**
	 * Add a point to the component
	 * @param point
	 */
	public void addPoint(Point point);
	
	/**
	 * Paint the component
	 * @param g - Graphics
	 */
	public void paintComponent(Graphics g);
}

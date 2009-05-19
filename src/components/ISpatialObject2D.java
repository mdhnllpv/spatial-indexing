package components;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface ISpatialObject2D {
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
		
	/**
	 * 
	 * @return - True if component is complete
	 */
	public boolean isComplete();
	
	/**
	 * 
	 * @param isComplete - true if component is complete
	 */
	public void setComplete(boolean isComplete);
	
	/**
	 * Set component caption
	 * @param caption - caption
	 */
	public void setCaption(String caption);
	
	/**
	 * 
	 * @return component bound
	 */
	public Rectangle getBound();
	
	public void setBound(Rectangle bound);
}

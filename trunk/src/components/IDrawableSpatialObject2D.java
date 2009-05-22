package components;

import java.awt.Color;
import java.awt.Graphics;

public interface IDrawableSpatialObject2D extends ISpatialObject2D {
	/**
	 * Draw the component
	 * @param g - Graphics
	 */
	public void draw(Graphics g);
	
	/**
	 * Set object color
	 * @param color
	 */
	public void setColor(Color color);
	
	/**
	 * Get object color
	 * @return
	 */
	public Color getColor();

}

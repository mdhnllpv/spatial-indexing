package index_structures;

import gui.MouseAction;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import components.ISpatialObject2D;

public interface IDrawableSpatialIndex extends ISpatialIndex {
	/**
	 * Draw the structure
	 * @param g Graphics
	 */
	public void draw(Graphics g);
	
	/**
	 * 
	 * @param evn
	 * @param object
	 */
	public void onMouseDragged(MouseEvent evn, ISpatialObject2D object, MouseAction action);
}

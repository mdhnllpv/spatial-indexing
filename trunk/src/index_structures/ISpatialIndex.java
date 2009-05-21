package index_structures;

import java.awt.Graphics;
import java.util.Collection;

import components.ISpatialObject2D;

public interface ISpatialIndex {
	/**
	 * Insert a object into index structure
	 * @param object object to insert
	 */
	public void Insert(ISpatialObject2D object);
	
	/**
	 * Insert hole collectin with elements
	 */
	public void Insert(Collection<ISpatialObject2D> collection);
	
	/**
	 * 
	 * @return True if the structure is empty
	 */
	public boolean isEmpty();
	
	/**
	 * Draw the structure
	 * @param g Graphics
	 */
	public void draw(Graphics g);
	
	/**
	 * Delete object from the structure
	 * @param object object to be deleted
	 */
	public boolean Delete(ISpatialObject2D object);
}

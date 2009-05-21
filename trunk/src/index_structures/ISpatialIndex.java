package index_structures;

import java.awt.Graphics;
import java.util.Collection;

import components.ISpatialObject2D;

public interface ISpatialIndex {
	/**
	 * Delete object from the structure
	 * @param object object to be deleted
	 */
	public boolean Delete(ISpatialObject2D object);
	
	/**
	 * Draw the structure
	 * @param g Graphics
	 */
	public void draw(Graphics g);
	
	/**
	 * Insert hole collectin with elements
	 */
	public void Insert(Collection<ISpatialObject2D> collection);
	
	/**
	 * Insert a object into index structure
	 * @param object object to insert
	 */
	public void Insert(ISpatialObject2D object);
	
	/**
	 * Search for a given object in the structure
	 * @param pattern searched pattern
	 * @return the found object
	 */
	public ISpatialObject2D Search(ISpatialObject2D pattern);
	
	/**
	 * 
	 * @return True if the structure is empty
	 */
	public boolean isEmpty();
}

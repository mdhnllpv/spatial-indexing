package index_structures;

import components.ISpatialObject2D;

public interface ISpatialIndex {
	/**
	 * Insert a object into index structure
	 * @param object object to insert
	 */
	public void Insert(ISpatialObject2D object);
}

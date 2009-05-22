package index_structures;

import java.awt.Graphics;

public interface IDrawableSpatialIndex extends ISpatialIndex {
	/**
	 * Draw the structure
	 * @param g Graphics
	 */
	public void draw(Graphics g);
}

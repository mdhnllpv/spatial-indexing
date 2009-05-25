package index_structures;

import index_structures.qtree.QuadTreeAdapter;
import index_structures.rtree.RTree;

public class SpatialIndexFactory {
	public static IDrawableSpatialIndex createSpatialIndex(SpatialIndex type) {

		if (type.equals(SpatialIndex.QuadTree)) {
			return new QuadTreeAdapter(0, 0, 700, 600);
		} else if (type.equals(SpatialIndex.RTree)) {
			return new RTree(2, 5);
		} else
			return null;
	}
}

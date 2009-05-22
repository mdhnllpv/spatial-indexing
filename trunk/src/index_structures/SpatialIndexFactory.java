package index_structures;

import index_structures.rtree.RTree;

import java.util.HashMap;
import java.util.Map;

public class SpatialIndexFactory {
	private static Map<SpatialIndex, IDrawableSpatialIndex> factory = new HashMap<SpatialIndex, IDrawableSpatialIndex>();
	
	static {
		factory.put(SpatialIndex.RTree, new RTree(3,5));
	}
	
	public static IDrawableSpatialIndex createSpatialIndex(SpatialIndex type){
		
		return factory.get(type);
	}
}

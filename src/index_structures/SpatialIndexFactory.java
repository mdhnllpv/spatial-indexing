package index_structures;

import index_structures.rtree.RTree;

import java.util.HashMap;
import java.util.Map;

public class SpatialIndexFactory {
	private static Map<SpatialIndex, ISpatialIndex> factory = new HashMap<SpatialIndex, ISpatialIndex>();
	
	static {
		factory.put(SpatialIndex.RTree, new RTree(3,5));
	}
	
	public static ISpatialIndex createSpatialIndex(SpatialIndex type){
		
		return factory.get(type);
	}
}

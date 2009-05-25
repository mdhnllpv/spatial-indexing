package index_structures.qtree;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;


public class QuadTree {
	
	private QuadNode root;
	
	public QuadTree(int x, int y, int height, int weight){
		root = new QuadLeaf(x,y,height,weight);
	}
	
	public void insert(Point point){
		root.insert(point);
	}
	
	public Set<Point> findPoints(Rectangle rect){
		return root.findPoints(rect);
	}
}

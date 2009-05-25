package index_structures.qtree;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;


public class QuadTree {
	
	private QuadNode root;
	
	public QuadTree(int x, int y, int width, int height){
		root = new QuadLeaf(x,y,width,height);
	}
	
	public void insert(Point point){
		root = root.insert(point);
	}
	
	public Set<Point> findPoints(Rectangle rect){
		return root.findPoints(rect);
	}
	
	public void draw(Graphics g){
		root.draw(g);
	}
}

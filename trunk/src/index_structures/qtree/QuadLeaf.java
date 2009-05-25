package index_structures.qtree;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadLeaf extends QuadNode {

	// Point per node
	private static int CAP = 2;

	// Point in the node
	private List<Point> points;

	public QuadLeaf(int x, int y, int w, int h) {
		super(x, y, w, h);
		points = new ArrayList<Point>();
	}

	@Override
	public Set<Point> findPoints(Rectangle r) {
		Set<Point> result = new HashSet<Point>();
		for (Point point : points ){
			if ( r.contains(point)){
				result.add(point);
			}
		}
		return result;
	}

	@Override
	public QuadNode insert(Point point) {
		if (!rect.contains(point)) {
			throw new IllegalArgumentException("Point not in node");
		}

		if (points.size() == CAP) {
			QuadBranch branch = new QuadBranch(rect.x, rect.y, rect.width,
					rect.height);
			for (Point p : points ){
				branch.insert(p);
			}
			branch.insert(point);
			return branch;
		} else {
			points.add(point);
			return this;
		}
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		for (Point point : points ){
			g.fillRect(point.x - 1, point.y - 1, 2, 2);
		}
	}

}

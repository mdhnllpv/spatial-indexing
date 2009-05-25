package index_structures.qtree;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

public class QuadBranch extends QuadNode {

	private static final int NW = 0;
	private static final int NE = 1;
	private static final int SE = 2;
	private static final int SW = 3;

	private QuadNode[] subNodes = new QuadNode[4];

	public QuadBranch(int x, int y, int w, int h) {
		super(x, y, w, h);
		int wh = w / 2;
		int hh = h / 2;
		subNodes[NW] = new QuadLeaf(rect.x, rect.y, wh, hh);
		subNodes[NE] = new QuadLeaf(rect.x + wh, rect.y, rect.width - wh, hh);
		subNodes[SE] = new QuadLeaf(rect.x + wh, rect.y + hh, rect.width - wh,
				rect.height - hh);
		subNodes[SW] = new QuadLeaf(rect.x, rect.y + hh, wh, rect.height - hh);
	}

	@Override
	public Set<Point> findPoints(Rectangle r) {
		Set<Point> result = new HashSet<Point>();
		for (QuadNode node : subNodes) {
			// If the intersection of the two triangles is not empty go
			// recursive
			if (!node.rect.intersection(r).isEmpty()) {
				result.addAll(node.findPoints(r));
			}
		}
		return result;
	}

	@Override
	public QuadNode insert(Point point) {
		for (int i = 0; i < 4; i++) {
			if (subNodes[i].contains(point)) {
				QuadNode n = subNodes[i].insert(point);
				// n may have been split into a branch with subnodes
				// so set subNodes[i] to returned node of insert operation
				subNodes[i] = n;
				break;
			}
		}
		return this;
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		for (int i = 0; i < 4; i++){
			subNodes[i].draw(g);
		}
	}

}

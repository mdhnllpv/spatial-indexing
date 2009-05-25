package index_structures.qtree;

import gui.MouseAction;
import index_structures.IDrawableSpatialIndex;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import components.ISpatialObject2D;
import components.SpatialObject2DImpl;

public class QuadTreeAdapter implements IDrawableSpatialIndex {

	private QuadTree tree;

	public QuadTreeAdapter(int x, int y, int width, int height) {
		tree = new QuadTree(x, y, width, height);
	}

	@Override
	public boolean Delete(ISpatialObject2D object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Insert(ISpatialObject2D object) {
		tree.insert(new Point(object.getPoints().get(0)));

	}

	@Override
	public Collection<ISpatialObject2D> Search(ISpatialObject2D pattern) {
		Set<ISpatialObject2D> res = new HashSet<ISpatialObject2D>();
		for (Point point : tree.findPoints(pattern.getBound())) {
			res.add(new SpatialObject2DImpl(new Rectangle(point.x, point.y, 0,
					0)));
		}
		return res;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		tree.draw(g);

	}

	@Override
	public void onMouseDragged(MouseEvent evn, ISpatialObject2D object,
			MouseAction action) {
		if (!action.equals(MouseAction.INSERT)) {
			object.addPoint(evn.getPoint());
		}

	}

}
